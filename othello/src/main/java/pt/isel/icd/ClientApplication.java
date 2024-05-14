package pt.isel.icd;

import pt.isel.icd.communication.Client;
import pt.isel.icd.communication.SimpleSocketManager;
import pt.isel.icd.game.management.GameClientController;
import pt.isel.icd.serialization.XMLSerializer;
import pt.isel.icd.user.management.StartClientView;
import pt.isel.icd.user.management.UserClientController;
import pt.isel.icd.user.management.UserClientService;

import java.io.IOException;

public class ClientApplication {
    public static void main(String[] args) throws IOException {
        SimpleSocketManager simpleSocketManager = new SimpleSocketManager();
        XMLSerializer xmlSerializer = new XMLSerializer();
        Client client = new Client(simpleSocketManager, xmlSerializer);
        UserClientService userClientService = new UserClientService();
        UserClientController userClientController = new UserClientController(simpleSocketManager, userClientService);
        GameClientController gameClientController = new GameClientController(simpleSocketManager);
        StartClientView startClientView = new StartClientView(userClientController);

        client.addController(gameClientController);
        client.addController(userClientController);
        client.connect();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        userClientController.authenticate("ner", "123456789");
    }
}
