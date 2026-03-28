package pt.isel.icd;

import java.io.IOException;
import pt.isel.icd.communication.SchemaValidator;
import pt.isel.icd.communication.Server;
import pt.isel.icd.communication.SimpleSocketManager;
import pt.isel.icd.database.XmlFileStore;
import pt.isel.icd.serialization.CommandRegistry;
import pt.isel.icd.serialization.CommandSerializer;
import pt.isel.icd.user.management.AuthenticationSimpleSocketMiddleware;
import pt.isel.icd.user.management.UserServerRepository;

public class ServerApplication {

    public static void main(String[] args) throws IOException {
        // Serialization
        CommandSerializer commandSerializer = new CommandSerializer();
        CommandRegistry.registerAll(commandSerializer);

        // Validation
        SchemaValidator schemaValidator = new SchemaValidator();

        // Communication
        SimpleSocketManager simpleSocketManager = new SimpleSocketManager();
        Server server = new Server(
            simpleSocketManager,
            commandSerializer,
            schemaValidator
        );

        // Database
        XmlFileStore xmlFileStore = new XmlFileStore();
        xmlFileStore.setFileStorePath("src/main/resources");

        // Repository
        UserServerRepository userServerRepository = new UserServerRepository(
            xmlFileStore
        );
        userServerRepository.loadUsers();
        userServerRepository.loadProfiles();

        // Controller
        ServerController serverController = new ServerController(
            userServerRepository,
            simpleSocketManager
        );

        // Middleware
        AuthenticationSimpleSocketMiddleware authMiddleware =
            new AuthenticationSimpleSocketMiddleware(serverController);
        simpleSocketManager.addMiddleware(authMiddleware);

        // Register controller
        server.addController(serverController);

        // Start server
        System.out.println("Starting Dots and Boxes Server...");
        server.listen();
    }
}
