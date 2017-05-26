package protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Protocol implements Runnable {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private boolean active;
    private boolean connected;
    private ArrayList<Endpoint> endpoints = new ArrayList<>();

    public Protocol(Socket socket) {
        this.socket = socket;
        this.connect();
        new Thread(this).start();
    }

    public void add_handler(Endpoint handler) {
        this.endpoints.add(handler);
    }

    public void rm_handler(Endpoint handler) {
        this.endpoints.remove(handler);
    }

    public void connect() {
        try {
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.writer = new PrintWriter(this.socket.getOutputStream(), true);
            this.connected = true;

        } catch (IOException e) { }

        for (Endpoint endpoint : this.endpoints) {
            endpoint.on_connect();
        }
    }

    public void disconnect() {
        this.writer.close();

        try {
            this.reader.close();
            this.socket.close();
            this.connected = false;

            for (Endpoint endpoint : this.endpoints) {
                endpoint.on_disconnect();
            }

        } catch (IOException e) { }
    }

    public boolean is_alive() {
        return this.active;
    }

    public boolean is_connected() {
        return this.connected;
    }

    public void terminate() {
        this.active = false;
        this.disconnect();
    }

    public void send(String message) {
        this.writer.println(message);
    }

    private void receive() {
        try {
            String message = this.reader.readLine();

            if (message != null) {
                this.broadcast(message);
            }
            else {
                this.disconnect();
            }

        } catch (IOException e) { }
    }

    private void broadcast(String message) {
        for (Endpoint handler : this.endpoints) {
            handler.on_received_message(message);
        }
    }

    @Override
    public void run() {
        this.active = true;

        while(this.active) {
            this.receive();

            if (!this.is_connected()) {
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) { }
            }
        }
    }

    public interface ProtocolHandler {

        void on_disconnect();
        void on_connect();
        void on_received_message(String message);
    }

    public interface Encoding {

        String name();
        String encode(String root, HashMap<String, String> attributes);
        String encode(String root, HashMap<String, String> attributes, String sub_element);
        HashMap<String, String> decode_attributes(String message);
        String decode_element(String message);
        String decode_sub_element(String message);
        String parse(HashMap<String, String> attributes);
        HashMap<String, String> parse(String attributes);
    }

    public interface Encoder {

        HashMap<String, String> attributes();
        void attributes(HashMap<String, String> attributes);
    }
}