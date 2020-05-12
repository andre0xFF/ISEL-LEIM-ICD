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
import application.protocol.requests.Logout;
import application.protocol.requests.Echo;
import application.protocol.responses.Denied;
import application.protocol.responses.Ok;

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
        this.format.register(new Logout());
        this.format.register(new Ok());
        this.format.register(new Denied());
        
        this.connect();

        new Thread(this).start();
    }

    public Client(String ip, int port) {
        this();
        
        server.ip = ip;
        server.port = port;
    }

    public void send(Message message) {
        String text = format.encode(message);
        this.server.out.println(text);
    }

    public void register(Request request) {
        this.format.register(request);
    }

    public static void main(String[] args) {
        new Client().send(new Message(new Echo()));
    }
    
}