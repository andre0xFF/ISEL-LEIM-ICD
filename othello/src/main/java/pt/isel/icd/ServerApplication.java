package pt.isel.icd;

import pt.isel.icd.communication.Server;
import pt.isel.icd.communication.SimpleSocketManager;
import pt.isel.icd.game.management.GameServerController;
import pt.isel.icd.serialization.XMLSerializer;
import pt.isel.icd.user.management.UserServerRepository;
import pt.isel.icd.user.management.UserServerController;
import pt.isel.icd.user.management.UserServerService;

import java.io.File;
import java.io.IOException;

public class ServerApplication {
    public static void main(String[] args) throws IOException {
        SimpleSocketManager simpleSocketManager = new SimpleSocketManager();
        XMLSerializer xmlSerializer = new XMLSerializer();
        Server server = new Server(simpleSocketManager, xmlSerializer);
        UserServerRepository userServerRepository = new UserServerRepository(xmlSerializer);
        UserServerService userServerService = new UserServerService(userServerRepository);
        UserServerController userServerController = new UserServerController(userServerService, simpleSocketManager);
        GameServerController gameServerController = new GameServerController(simpleSocketManager);

        userServerRepository.addFile("users", new File("src/main/resources/user/management/Users.xml"));
        userServerRepository.addFile("profiles", new File("src/main/resources/user/management/Profiles.xml"));
        userServerRepository.loadUsers();
        userServerRepository.loadProfiles();

        server.addController(gameServerController);
        server.addController(userServerController);
        server.listen();
    }
}
