package pt.isel.icd.communication;

import pt.isel.icd.patterns.verticals.Controller;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private final SocketService socketService;

    public Server(SocketService existingSocketService) {
        socketService = existingSocketService;
    }

    public void listen() throws IOException {
        ServerSocket serverSocket = new ServerSocket(SimpleSocket.DEFAULT_PORT);

        while (true) {
            SimpleSocket clientSocket = new SimpleSocket(serverSocket.accept());
            ClientHandler clientHandler = new ClientHandler(socketService, clientSocket);
        }
    }

    public void addController(Controller controller) {
        socketService.addController(controller);
    }

    public void removeController(Controller controller) {
        socketService.removeController(controller);
    }
}
