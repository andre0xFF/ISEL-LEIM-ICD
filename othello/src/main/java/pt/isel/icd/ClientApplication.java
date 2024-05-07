package pt.isel.icd;

import pt.isel.icd.communication.Client;
import pt.isel.icd.communication.SimpleSocketManager;
import pt.isel.icd.game.management.GameClientController;
import pt.isel.icd.user.management.UserClientController;

import java.io.IOException;

public class ClientApplication {
    public static void main(String[] args) throws IOException {
        SimpleSocketManager simpleSocketManager = new SimpleSocketManager();
        Client client = new Client(simpleSocketManager);
        GameClientController gameClientController = new GameClientController(simpleSocketManager);
        UserClientController userClientController = new UserClientController(simpleSocketManager);

        client.addController(gameClientController);
        client.addController(userClientController);

        client.connect();
    }
}
