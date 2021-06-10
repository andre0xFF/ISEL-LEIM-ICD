package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import application.protocol.Format;
import application.protocol.Message;
import application.protocol.Request;
import application.protocol.formats.XML;
import application.protocol.requests.Connect;
import application.protocol.requests.Disconnect;
import application.protocol.requests.Echo;

public class Client implements Runnable {

    private class Server {
        String ip = "localhost";
        int port = 9999;
        Socket socket;
        PrintWriter out;
        BufferedReader in;
        boolean isConnected;
    }

    //
    // Functionality
    //
    Format format = new XML();
    Server server = new Server();

    @Override
    public void run() {
        while(this.server.isConnected) {
            this.receive().execute(this);
        }
    }

    private Message receive() {
        try {
            String input = this.server.in.readLine();
            System.out.println(input);
            return this.format.decode(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean connect() {
        if (this.server.ip == null) {
            // TODO server discover
        }

        try {
            this.server.socket = new Socket(server.ip, server.port);
            this.server.out = new PrintWriter(server.socket.getOutputStream(), true);
            this.server.in = new BufferedReader(new InputStreamReader(server.socket.getInputStream()));
        } catch (IOException e) {
            server.isConnected = false;
            return false;
        }

        this.server.isConnected = true;

        return true;
    }

    //
    // Interface
    //
    public Client() {
        this.format.register(new Echo());
        this.format.register(new Connect());
        this.format.register(new Disconnect());
        
        this.connect();

        new Thread(this).start();
    }

    public Client(String ip, int port) {
        this();
        
        server.ip = ip;
        server.port = port;
    }

    public void send(Request request) {
        Message message = new Message(request);
        String text = format.encode(message);
        this.server.out.println(text);
    }

    public void register(Request request) {
        this.format.register(request);
    }

    public void disconnect() {
        
    }
    
}