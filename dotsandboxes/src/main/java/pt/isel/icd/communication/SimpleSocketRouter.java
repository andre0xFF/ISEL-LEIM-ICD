package pt.isel.icd.communication;

import java.util.ArrayList;
import java.util.HashMap;

public class SimpleSocketRouter {

    private final HashMap<
        Class<? extends SimpleSocketCommand<?>>,
        Object
    > controllers = new HashMap<>();
    private final ArrayList<SimpleSocketMiddleware> middlewares =
        new ArrayList<>();
    private SimpleSocketCommand<Object> command;

    public void addReceiver(
        Class<? extends SimpleSocketCommand<?>> commandType,
        Object controller
    ) {
        controllers.put(commandType, controller);
    }

    public void removeReceiver(
        Class<? extends SimpleSocketCommand<?>> commandType
    ) {
        controllers.remove(commandType);
    }

    public void addMiddleware(SimpleSocketMiddleware middleware) {
        middlewares.add(middleware);
    }

    public void removeMiddleware(SimpleSocketMiddleware middleware) {
        middlewares.remove(middleware);
    }

    @SuppressWarnings("unchecked")
    public void route(SimpleSocketCommand<?> newCommand) {
        command = (SimpleSocketCommand<Object>) newCommand;

        Class<?> commandType = command.getClass();
        Object receiver = controllers.get(commandType);

        if (receiver == null) {
            return;
        }

        for (SimpleSocketMiddleware middleware : middlewares) {
            boolean handled = middleware.handle(command);
            if (!handled) {
                return;
            }
        }

        command.setReceiver(receiver);
        command.execute();
    }
}
