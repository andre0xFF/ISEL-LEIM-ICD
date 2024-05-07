package pt.isel.icd;

import pt.isel.icd.communication.Server;
import pt.isel.icd.communication.SimpleSocketManager;
import pt.isel.icd.game.management.GameServerController;
import pt.isel.icd.user.management.UserServerController;

import java.io.IOException;

public class ServerApplication {
    public static void main(String[] args) throws IOException {
        SimpleSocketManager simpleSocketManager = new SimpleSocketManager();
        Server server = new Server(simpleSocketManager);

        server.addController(new GameServerController(simpleSocketManager));
        server.addController(new UserServerController(simpleSocketManager));

        server.listen();
    }
}
