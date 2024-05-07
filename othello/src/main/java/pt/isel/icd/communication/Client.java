package pt.isel.icd.communication;

import pt.isel.icd.patterns.verticals.Controller;

import java.io.IOException;

public class Client {

    private final SocketService socketService;

    public Client(SocketService existingSocketService) {
        socketService = existingSocketService;
    }

    public void connect() throws IOException {
        SimpleSocket simpleSocket = new SimpleSocket();
        ClientHandler clientHandler = new ClientHandler(socketService, simpleSocket);
    }

    public void addController(Controller controller) {
        socketService.addController(controller);
    }

    public void removeController(Controller controller) {
        socketService.removeController(controller);
    }
}
