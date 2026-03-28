package pt.isel.icd.communication;

import java.util.ArrayList;
import java.util.HashMap;

public class SimpleSocketRouter {

    private final HashMap<Class<? extends Command<?>>, Object> controllers =
        new HashMap<>();
    private final ArrayList<SimpleSocketMiddleware> middlewares =
        new ArrayList<>();
    private Command<Object> command;

    public void addReceiver(
        Class<? extends Command<?>> commandType,
        Object controller
    ) {
        controllers.put(commandType, controller);
    }

    public void removeReceiver(Class<? extends Command<?>> commandType) {
        controllers.remove(commandType);
    }

    public void addMiddleware(SimpleSocketMiddleware middleware) {
        middlewares.add(middleware);
    }

    public void removeMiddleware(SimpleSocketMiddleware middleware) {
        middlewares.remove(middleware);
    }

    @SuppressWarnings("unchecked")
    public void route(Command<?> newCommand) {
        command = (Command<Object>) newCommand;

        Class<?> commandType = command.getClass();
        Object receiver = controllers.get(commandType);

        if (receiver == null) {
            return;
        }

        if (command instanceof SimpleSocketCommand<?> socketCommand) {
            for (SimpleSocketMiddleware middleware : middlewares) {
                boolean handled = middleware.handle(socketCommand);
                if (!handled) {
                    return;
                }
            }
        }

        command.setReceiver(receiver);
        command.execute();
    }
}
