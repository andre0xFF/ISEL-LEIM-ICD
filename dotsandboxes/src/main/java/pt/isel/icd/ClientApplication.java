package pt.isel.icd;

import java.io.IOException;
import pt.isel.icd.communication.Client;
import pt.isel.icd.communication.SchemaValidator;
import pt.isel.icd.communication.SimpleSocketManager;
import pt.isel.icd.serialization.CommandRegistry;
import pt.isel.icd.serialization.CommandSerializer;
import pt.isel.icd.user.logic.User;
import pt.isel.icd.user.management.AuthenticationSimpleSocketMiddleware;

public class ClientApplication {

    public static void main(String[] args)
        throws IOException, InterruptedException {
        // Serialization
        CommandSerializer commandSerializer = new CommandSerializer();
        CommandRegistry.registerAll(commandSerializer);

        // Validation
        SchemaValidator schemaValidator = new SchemaValidator();

        // Communication
        SimpleSocketManager simpleSocketManager = new SimpleSocketManager();
        Client client = new Client(
            simpleSocketManager,
            commandSerializer,
            schemaValidator
        );

        // Controller
        ClientController clientController = new ClientController(
            simpleSocketManager
        );

        // Middleware
        AuthenticationSimpleSocketMiddleware authMiddleware =
            new AuthenticationSimpleSocketMiddleware(clientController);
        simpleSocketManager.addMiddleware(authMiddleware);

        // Register controller
        client.addController(clientController);

        // Connect to server
        System.out.println("Connecting to Dots and Boxes Server...");
        client.connect();

        // Wait for connection to establish
        Thread.sleep(1000);

        // Example: authenticate
        clientController.authenticateUser(new User("player1", "password1234"));

        // Wait for response
        Thread.sleep(1000);

        // Example: read profile
        clientController.readUserProfile();

        // Wait for response
        Thread.sleep(1000);

        // Example: join game
        clientController.joinGame();
    }
}
