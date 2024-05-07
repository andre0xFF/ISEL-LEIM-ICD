package pt.isel.icd.communication;

import pt.isel.icd.patterns.chainofresponsability.Handler;
import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;
import pt.isel.icd.patterns.verticals.Controller;
import pt.isel.icd.patterns.verticals.Service;

import java.util.ArrayList;

public class SocketService implements Service {
    private final Router router = new Router();
    private final ArrayList<SimpleSocket> clients = new ArrayList<>();
    private final ArrayList<Handler<Command<Receiver>>> handlers = new ArrayList<>();

    public void connectClient(SimpleSocket client) {
        clients.add(client);
    }

    public void disconnectClient(SimpleSocket client) {
        clients.remove(client);
    }

    public void addController(Controller controller) {
        ArrayList<Class<? extends Command<? extends Receiver>>> commands = controller.commandsList();

        for (Class<? extends Command<? extends Receiver>> commandClass : commands){
            addReceiver(commandClass, controller);
        }
    }

    public void removeController(Controller controller) {
        ArrayList<Class<? extends Command<? extends Receiver>>> commands = controller.commandsList();

        for (Class<? extends Command<? extends Receiver>> commandClass : commands){
            removeReceiver(commandClass);
        }
    }

    public void addReceiver(Class<? extends Command<? extends Receiver>> commandType, Receiver controller) {
        router.addReceiver(commandType, controller);
    }

    public void removeReceiver(Class<? extends Command<? extends Receiver>> commandType) {
        router.removeReceiver(commandType);
    }

    public void addHandler(Handler<Command<Receiver>> handler) {
        handlers.add(handler);
    }

    public void route(Command<Receiver> command) {
        for (Handler<Command<Receiver>> handler : handlers) {
            handler.handle(command);
        }

        router.route(command);
    }
}
