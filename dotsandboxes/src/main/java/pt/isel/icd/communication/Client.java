package pt.isel.icd.communication;

import java.io.IOException;
import pt.isel.icd.serialization.CommandSerializer;

public class Client {

    private final SimpleSocketManager simpleSocketManager;
    private final CommandSerializer commandSerializer;
    private final SchemaValidator schemaValidator;

    public Client(
        SimpleSocketManager manager,
        CommandSerializer serializer,
        SchemaValidator validator
    ) {
        simpleSocketManager = manager;

        commandSerializer = serializer;
        schemaValidator = validator;
    }

    public void connect() throws IOException {
        SimpleSocket simpleSocket = new SimpleSocket(
            commandSerializer,
            schemaValidator
        );
        new ClientHandler(simpleSocketManager, simpleSocket);
    }

    public void addController(Controller controller) {
        simpleSocketManager.addController(controller);
    }

    public void removeController(Controller controller) {
        simpleSocketManager.removeController(controller);
    }

    public void sendCommand(SimpleSocketCommand<?> command) {
        simpleSocketManager.write(command);
    }
}
