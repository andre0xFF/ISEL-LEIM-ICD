package network;

import network.messages.Message;
import network.socket.Socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a server.
 */
public class Server {
    private final int port;
    private Boolean running = true;
    private final HashMap<String, Class<? extends Message>> messages = new HashMap<>();

    public Server() {
        this(Socket.DEFAULT_PORT);
    }

    public Server(int port) {
        this.port = port;
    }

    public Server(ArrayList<Class<? extends Message>> messages) {
        this(messages, Socket.DEFAULT_PORT);
    }

    public Server(ArrayList<Class<? extends Message>> messages, int port) {
        this.port = port;

        for (Class<? extends Message> message : messages) {
            addMessage(message);
        }
    }

    /**
     * Starts the server.
     */
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (running) {
                Socket socket = new Socket(serverSocket.accept());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the server.
     */
    public void stop() {
        this.running = false;
    }

    private void addMessage(Class<? extends Message> clazz) {
        this.messages.put(clazz.toString(), clazz);
    }

    public int port() {
        return this.port;
    }

    public Client accept() throws IOException {
        Socket socket;

        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            socket = new Socket(serverSocket.accept());
        }

        return new Client(socket);
    }
}
