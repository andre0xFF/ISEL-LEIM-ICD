package pt.isel.icd.communication;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import pt.isel.icd.user.management.Authenticator;

public class SimpleSocketManager implements ConnectionManager {

    private final SimpleSocketRouter router = new SimpleSocketRouter();
    private final ArrayList<SimpleSocket> sockets = new ArrayList<>();

    protected void connectClient(SimpleSocket client) {
        sockets.add(client);
    }

    protected void disconnectClient(SimpleSocket client) {
        sockets.remove(client);
    }

    protected void addController(Controller controller) {
        List<Class<? extends SimpleSocketCommand<?>>> commands =
            controller.commandsList();
        for (Class<? extends SimpleSocketCommand<?>> commandClass : commands) {
            router.addReceiver(commandClass, controller);
        }
    }

    protected void removeController(Controller controller) {
        List<Class<? extends SimpleSocketCommand<?>>> commands =
            controller.commandsList();
        for (Class<? extends SimpleSocketCommand<?>> commandClass : commands) {
            router.removeReceiver(commandClass);
        }
    }

    protected void route(SimpleSocketCommand<?> command) {
        router.route(command);
    }

    @Override
    public void write(UUID clientIdentifier, SimpleSocketCommand<?> command) {
        for (SimpleSocket socket : sockets) {
            if (socket.identifier().equals(clientIdentifier)) {
                socket.write(command);
                return;
            }
        }
    }

    @Override
    public void write(SimpleSocketCommand<?> command) {
        for (SimpleSocket socket : sockets) {
            socket.write(command);
        }
    }

    public void setAuthenticator(Authenticator authenticator) {
        router.setAuthenticator(authenticator);
    }
}
