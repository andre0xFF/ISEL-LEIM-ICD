package pt.isel.icd.communication;

import pt.isel.icd.patterns.verticals.Controller;
import pt.isel.icd.serialization.Serializer;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private final SimpleSocketManager simpleSocketManager;
    private final Serializer serializer;

    public Server(SimpleSocketManager existingSimpleSocketManager, Serializer existingSerializer) {
        simpleSocketManager = existingSimpleSocketManager;
        serializer = existingSerializer;
    }

    public void listen() throws IOException {
        ServerSocket serverSocket = new ServerSocket(SimpleSocket.DEFAULT_PORT);

        while (true) {
            SimpleSocket clientSocket = new SimpleSocket(serializer, serverSocket.accept());
            ClientHandler clientHandler = new ClientHandler(simpleSocketManager, clientSocket, serializer);
        }
    }

    public void addController(Controller controller) {
        simpleSocketManager.addController(controller);
    }

    public void removeController(Controller controller) {
        simpleSocketManager.removeController(controller);
    }
}
