package pt.isel.icd;

import pt.isel.icd.communication.Server;
import pt.isel.icd.communication.SimpleSocketManager;
import pt.isel.icd.database.XmlFileStore;
import pt.isel.icd.game.management.GameServerController;
import pt.isel.icd.serialization.XMLSerializer;
import pt.isel.icd.user.management.AuthenticationSimpleSocketMiddleware;
import pt.isel.icd.user.management.UserServerRepository;
import pt.isel.icd.user.management.UserServerController;

import java.io.IOException;

public class ServerApplication {
    public static void main(String[] args) throws IOException {
        SimpleSocketManager simpleSocketManager = new SimpleSocketManager();
        XMLSerializer xmlSerializer = new XMLSerializer();
        Server server = new Server(simpleSocketManager, xmlSerializer);
        XmlFileStore xmlFileStore = new XmlFileStore(xmlSerializer);
        UserServerRepository userServerRepository = new UserServerRepository(xmlFileStore);
        UserServerController userServerController = new UserServerController(userServerRepository, simpleSocketManager);
        GameServerController gameServerController = new GameServerController(simpleSocketManager);
        AuthenticationSimpleSocketMiddleware authenticationSimpleSocketMiddleware = new AuthenticationSimpleSocketMiddleware(userServerController);

        xmlFileStore.setFileStorePath("src/main/resources/");

        simpleSocketManager.addMiddleware(authenticationSimpleSocketMiddleware);

        userServerRepository.loadUsers();
        userServerRepository.loadProfiles();

        server.addController(gameServerController);
        server.addController(userServerController);
        server.listen();
    }
}
