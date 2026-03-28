package pt.isel.icd.communication;

import java.io.IOException;
import java.net.ServerSocket;
import pt.isel.icd.serialization.CommandSerializer;

public class Server {

    private final SimpleSocketManager simpleSocketManager;
    private final CommandSerializer commandSerializer;
    private final SchemaValidator schemaValidator;

    public Server(
        SimpleSocketManager manager,
        CommandSerializer serializer,
        SchemaValidator validator
    ) {
        simpleSocketManager = manager;
        commandSerializer = serializer;
        schemaValidator = validator;
    }

    public void listen() throws IOException {
        ServerSocket serverSocket = new ServerSocket(SimpleSocket.DEFAULT_PORT);
        System.out.println(
            "Server listening on port " + SimpleSocket.DEFAULT_PORT + "..."
        );

        while (true) {
            SimpleSocket clientSocket = new SimpleSocket(
                commandSerializer,
                schemaValidator,
                serverSocket.accept()
            );
            new ClientHandler(simpleSocketManager, clientSocket);
        }
    }

    public void addController(Controller controller) {
        simpleSocketManager.addController(controller);
    }

    public void removeController(Controller controller) {
        simpleSocketManager.removeController(controller);
    }
}
