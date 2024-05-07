package pt.isel.icd;

import pt.isel.icd.communication.Server;
import pt.isel.icd.communication.SocketService;
import pt.isel.icd.game.management.GameServerController;
import pt.isel.icd.user.management.UserServerController;

import java.io.IOException;

public class ServerApplication {
    public static void main(String[] args) throws IOException {
        Server server = new Server(new SocketService());

        server.addController(new GameServerController());
        server.addController(new UserServerController());

        server.listen();
    }
}
