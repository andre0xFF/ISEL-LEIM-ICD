package pt.isel.icd.communication;

import pt.isel.icd.patterns.verticals.Controller;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private final SimpleSocketManager simpleSocketManager;

    public Server(SimpleSocketManager existingSimpleSocketManager) {
        simpleSocketManager = existingSimpleSocketManager;
    }

    public void listen() throws IOException {
        ServerSocket serverSocket = new ServerSocket(SimpleSocket.DEFAULT_PORT);

        while (true) {
            SimpleSocket clientSocket = new SimpleSocket(serverSocket.accept());
            ClientHandler clientHandler = new ClientHandler(simpleSocketManager, clientSocket);
        }
    }

    public void addController(Controller controller) {
        simpleSocketManager.addController(controller);
    }

    public void removeController(Controller controller) {
        simpleSocketManager.removeController(controller);
    }
}
