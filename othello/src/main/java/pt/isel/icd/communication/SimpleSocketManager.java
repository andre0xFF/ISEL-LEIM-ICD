package pt.isel.icd.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;
import pt.isel.icd.patterns.verticals.Controller;
import pt.isel.icd.serialization.Serializer;

import java.util.ArrayList;
import java.util.UUID;

public class SimpleSocketManager implements ConnectionManager {
    private final Router router = new Router();
    private final ArrayList<SimpleSocket> simpleSockets = new ArrayList<>();
    private final Serializer serializer;

    public SimpleSocketManager(Serializer existingSerializer) {
        serializer = existingSerializer;
    }

    protected void connectClient(SimpleSocket client) {
        simpleSockets.add(client);
    }

    protected void disconnectClient(SimpleSocket client) {
        simpleSockets.remove(client);
    }

    protected void addController(Controller controller) {
        ArrayList<Class<? extends Command<? extends Receiver>>> commands = controller.commandsList();

        for (Class<? extends Command<? extends Receiver>> commandClass : commands){
            addReceiver(commandClass, controller);
        }
    }

    protected void removeController(Controller controller) {
        ArrayList<Class<? extends Command<? extends Receiver>>> commands = controller.commandsList();

        for (Class<? extends Command<? extends Receiver>> commandClass : commands){
            removeReceiver(commandClass);
        }
    }

    protected void addReceiver(Class<? extends Command<? extends Receiver>> commandType, Receiver controller) {
        router.addReceiver(commandType, controller);
    }

    protected void removeReceiver(Class<? extends Command<? extends Receiver>> commandType) {
        router.removeReceiver(commandType);
    }

    protected void route(Command<Receiver> command) {
        router.route(command);
    }

//    @Override
//    public Command<?> readCommand(UUID clientIdentifier) throws IOException, IllegalArgumentException {
//        for (SimpleSocket simpleSocket : simpleSockets) {
//            if (simpleSocket.identifier().equals(clientIdentifier)) {
//                String line = simpleSocket.readLine();
//
//                return serializer.deserialize(line, Command.class);
//            }
//        }
//
//        throw new IllegalArgumentException("Client not found");
//    }

    protected void sendCommand(UUID clientIdentifier, Command<?> command) {
        for (SimpleSocket simpleSocket : simpleSockets) {
            if (simpleSocket.identifier().equals(clientIdentifier)) {
                // TODO Serialize message and write to simple socket
                // String message = command.serialize();
                // simpleSocket.write(message);
            }
        }
    }

    @Override
    public void sendCommand(Command<?> command) throws JsonProcessingException {
        for (SimpleSocket simpleSocket : simpleSockets) {
            String line = serializer.serialize(command);

            simpleSocket.write(line);
        }
    }

    @Override
    public void addMiddleware(Middleware middleware) {
        router.addMiddleware(middleware);
    }

    @Override
    public void removeMiddleware(Middleware middleware) {
        router.removeMiddleware(middleware);
    }
}
