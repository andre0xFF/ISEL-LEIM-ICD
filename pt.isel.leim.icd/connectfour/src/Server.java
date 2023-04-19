import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    private final int port;
    private Boolean running = true;
    private final HashMap<String, Class<? extends Message>> messages = new HashMap<>();
    private final ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    public Server(ArrayList<Class<? extends Message>> messages) {
        this(messages, 8000);
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
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (running) {
                Socket socket = serverSocket.accept();

                clientHandlers.add(new ClientHandler(socket));
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

    public int getPort() {
        return port;
    }

    /**
     * A client handler handles a client.
     */
    class ClientHandler implements Runnable {
        private final Socket socket;
        private final PrintWriter writer;
        private final BufferedReader reader;
        private final Message.Serializer serializer = new Message.Serializer();

        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            this.writer = new PrintWriter(socket.getOutputStream(), true);
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(this).start();
        }

        @Override
        public void run() {
            try {
                String content = reader.readLine();
                Message message = serializer.deserialize(content);

                var targetClass = message.getClass().toString();

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private Message read() {
            try {
                String content = reader.readLine();

                return serializer.deserialize(content);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void write(Message message) throws JsonProcessingException {
            String content = serializer.serialize(message);
            writer.println(content);
        }
    }
}
