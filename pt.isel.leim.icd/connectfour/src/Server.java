import messages.Message;
import network.Socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    private final int port;
    private Boolean running = true;
    private final HashMap<String, Class<? extends Message>> messages = new HashMap<>();

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
        running = false;
    }

    private void addMessage(Class<? extends Message> clazz) {
        messages.put(clazz.toString(), clazz);
    }

    public int port() {
        return port;
    }
}
