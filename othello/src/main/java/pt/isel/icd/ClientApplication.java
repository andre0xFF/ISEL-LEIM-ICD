package pt.isel.icd;

import pt.isel.icd.communication.Client;
import pt.isel.icd.communication.SimpleSocketManager;
import pt.isel.icd.game.management.GameClientController;
import pt.isel.icd.serialization.XMLSerializer;
import pt.isel.icd.user.logic.User;
import pt.isel.icd.user.management.*;

import java.io.IOException;

public class ClientApplication {
    public static void main(String[] args) throws IOException {
        SimpleSocketManager simpleSocketManager = new SimpleSocketManager();
        XMLSerializer xmlSerializer = new XMLSerializer();
        Client client = new Client(simpleSocketManager, xmlSerializer);
        UserClientRepository userClientRepository = new UserClientRepository();
        UserClientController userClientController = new UserClientController(userClientRepository, simpleSocketManager);
        GameClientController gameClientController = new GameClientController(simpleSocketManager);
        UserFrame userFrame = new UserFrame();
        StartClientView startClientView = new StartClientView(userFrame, userClientController);

        simpleSocketManager.addMiddleware(new AuthenticationSimpleSocketMiddleware(userClientController));

        client.addController(gameClientController);
        client.addController(userClientController);
        client.connect();

        userClientController.authenticateUser(new User("user11", "password1234"));
    }
}
