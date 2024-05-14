package pt.isel.icd.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import pt.isel.icd.patterns.verticals.Controller;
import pt.isel.icd.serialization.Serializer;

import java.io.IOException;

public class Client {

    private final SimpleSocketManager simpleSocketManager;
    private final Serializer serializer;

    public Client(SimpleSocketManager existingSimpleSocketManager, Serializer existingSerializer) {
        simpleSocketManager = existingSimpleSocketManager;
        serializer = existingSerializer;
    }

    public void connect() throws IOException {
        SimpleSocket simpleSocket = new SimpleSocket(serializer);
        ClientHandler clientHandler = new ClientHandler(simpleSocketManager, simpleSocket);
    }

    public void addController(Controller controller) {
        simpleSocketManager.addController(controller);
    }

    public void removeController(Controller controller) {
        simpleSocketManager.removeController(controller);
    }

    public void sendCommand(SimpleSocketCommand<?> simpleSocketCommand) throws JsonProcessingException {
        simpleSocketManager.write(simpleSocketCommand);
    }
}
