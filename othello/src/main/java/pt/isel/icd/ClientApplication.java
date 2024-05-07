package pt.isel.icd;

import pt.isel.icd.communication.Client;
import pt.isel.icd.communication.SocketService;
import pt.isel.icd.game.management.GameClientController;
import pt.isel.icd.user.management.UserClientController;

import java.io.IOException;

public class ClientApplication {
    public static void main(String[] args) throws IOException {
        Client client = new Client(new SocketService());

        client.addController(new GameClientController());
        client.addController(new UserClientController());

        client.connect();
    }
}
