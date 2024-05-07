package pt.isel.icd.communication;

import pt.isel.icd.patterns.verticals.Controller;

import java.io.IOException;

public class Client {

    private final SimpleSocketManager simpleSocketManager;

    public Client(SimpleSocketManager existingSimpleSocketManager) {
        simpleSocketManager = existingSimpleSocketManager;
    }

    public void connect() throws IOException {
        SimpleSocket simpleSocket = new SimpleSocket();
        ClientHandler clientHandler = new ClientHandler(simpleSocketManager, simpleSocket);
    }

    public void addController(Controller controller) {
        simpleSocketManager.addController(controller);
    }

    public void removeController(Controller controller) {
        simpleSocketManager.removeController(controller);
    }
}
