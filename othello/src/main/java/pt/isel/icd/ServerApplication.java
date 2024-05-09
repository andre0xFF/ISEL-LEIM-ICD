package pt.isel.icd;

import pt.isel.icd.communication.Server;
import pt.isel.icd.communication.SimpleSocketManager;
import pt.isel.icd.game.management.GameServerController;
import pt.isel.icd.serialization.XMLSerializer;
import pt.isel.icd.user.management.UserServerController;

import java.io.IOException;

public class ServerApplication {
    public static void main(String[] args) throws IOException {
        XMLSerializer xmlSerializer = new XMLSerializer();
        SimpleSocketManager simpleSocketManager = new SimpleSocketManager(xmlSerializer);
        Server server = new Server(simpleSocketManager, xmlSerializer);
        GameServerController gameServerController = new GameServerController(simpleSocketManager);
        UserServerController userServerController = new UserServerController(simpleSocketManager);

        server.addController(gameServerController);
        server.addController(userServerController);

        server.listen();
    }
}
