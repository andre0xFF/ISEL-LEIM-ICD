package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import application.protocol.Message;

public class ServerThread implements Runnable {

    PrintWriter out;
    BufferedReader in;
    Socket socket;
    boolean isConnected;

    Server server;
    User user;

    public ServerThread(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;

        this.start();

        new Thread(this).start();
    }

    @Override
    public void run() {
        while (this.isConnected) {
            this.receive().execute(this);
        }
    }

    private Message receive() {
        try {
            String input = this.in.readLine();
            System.out.println(input);
            return this.server.get_format().decode(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void start() {
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.isConnected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        try {
            this.isConnected = false;
            this.in.close();
            this.out.close();
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public void send(Message message) {
        String text = this.server.get_format().encode(message);
        this.out.println(text);
    }

    public void send(Message message, User user) {
        this.server.send(message, user);
    }
    
    public User get_user() {
        return this.user;
    }
}