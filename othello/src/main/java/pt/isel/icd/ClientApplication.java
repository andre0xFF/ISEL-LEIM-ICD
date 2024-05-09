package pt.isel.icd;

import pt.isel.icd.communication.Client;
import pt.isel.icd.communication.SimpleSocketManager;
import pt.isel.icd.game.management.GameClientController;
import pt.isel.icd.serialization.XMLSerializer;
import pt.isel.icd.user.management.AuthenticateUserCommand;
import pt.isel.icd.user.management.UserClientController;

import java.io.IOException;

public class ClientApplication {
    public static void main(String[] args) throws IOException {
        XMLSerializer xmlSerializer = new XMLSerializer();
        SimpleSocketManager simpleSocketManager = new SimpleSocketManager(xmlSerializer);
        Client client = new Client(simpleSocketManager, xmlSerializer);
        UserClientController userClientController = new UserClientController(simpleSocketManager);
        GameClientController gameClientController = new GameClientController(simpleSocketManager);

        client.addController(gameClientController);
        client.addController(userClientController);

        client.connect();

        client.sendCommand(new AuthenticateUserCommand("user", "da23e"));
    }
}
