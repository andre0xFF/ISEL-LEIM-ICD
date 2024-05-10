package pt.isel.icd.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;
import pt.isel.icd.patterns.verticals.Controller;

import java.util.ArrayList;
import java.util.UUID;

public class SimpleSocketManager implements ConnectionManager {
    private final SimpleSocketRouter simpleSocketRouter = new SimpleSocketRouter();
    private final ArrayList<SimpleSocket> simpleSockets = new ArrayList<>();

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
        simpleSocketRouter.addReceiver(commandType, controller);
    }

    protected void removeReceiver(Class<? extends Command<? extends Receiver>> commandType) {
        simpleSocketRouter.removeReceiver(commandType);
    }

    protected void route(Command<Receiver> command) {
        simpleSocketRouter.route(command);
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

    @Override
    public void write(UUID clientIdentifier, SimpleSocketCommand<?> simpleSocketCommand) throws JsonProcessingException {
        for (SimpleSocket simpleSocket : simpleSockets) {
            if (simpleSocket.identifier().equals(clientIdentifier)) {
                simpleSocket.write(simpleSocketCommand);
                return;
            }
        }
    }

    @Override
    public void write(SimpleSocketCommand<?> simpleSocketCommand) throws JsonProcessingException {
        for (SimpleSocket simpleSocket : simpleSockets) {
            simpleSocket.write(simpleSocketCommand);
        }
    }

    @Override
    public void addMiddleware(SimpleSocketMiddleware simpleSocketMiddleware) {
        simpleSocketRouter.addMiddleware(simpleSocketMiddleware);
    }

    @Override
    public void removeMiddleware(SimpleSocketMiddleware simpleSocketMiddleware) {
        simpleSocketRouter.removeMiddleware(simpleSocketMiddleware);
    }
}
