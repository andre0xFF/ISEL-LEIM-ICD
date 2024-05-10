package pt.isel.icd.communication;


import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Invoker;
import pt.isel.icd.patterns.command.Receiver;

import java.util.ArrayList;
import java.util.HashMap;

public class SimpleSocketRouter implements Invoker<Receiver> {

    private final HashMap<Class<? extends Command<? extends Receiver>>, Receiver> controllers = new HashMap<>();
    private final ArrayList<SimpleSocketMiddleware> simpleSocketMiddlewares = new ArrayList<>();
    private Command<Receiver> command;

    public void addReceiver(Class<? extends Command<? extends Receiver>> commandType, Receiver controller) {
        controllers.put(commandType, controller);
    }

    public void removeReceiver(Class<? extends Command<? extends Receiver>> commandType) {
        controllers.remove(commandType);
    }

    public void addMiddleware(SimpleSocketMiddleware simpleSocketMiddleware) {
        simpleSocketMiddlewares.add(simpleSocketMiddleware);
    }

    public void removeMiddleware(SimpleSocketMiddleware simpleSocketMiddleware) {
        simpleSocketMiddlewares.remove(simpleSocketMiddleware);
    }

    public void route(Command<Receiver> newCommand) {
        setCommand(newCommand);
        executeCommand();
    }

    @Override
    public void setCommand(Command<Receiver> newCommand) {
        command = newCommand;
    }

    public void setConnectionCommand(SimpleSocketCommand<Receiver> newCommand) {
        command = newCommand;
    }

    @Override
    public void executeCommand() {
        Class<?> commandType = command.getClass();
        Receiver receiver = controllers.get(commandType);

        if (receiver == null) {
            return;
        }

        // TODO: Shame! This is a hack to allow the middleware to handle the connection command.
        if (command instanceof SimpleSocketCommand<Receiver>) {
            for (SimpleSocketMiddleware simpleSocketMiddleware : simpleSocketMiddlewares) {
                boolean isHandled = simpleSocketMiddleware.handle((SimpleSocketCommand<?>) command);

                if (!isHandled) {
                    return;
                }
            }
        }

        command.setReceiver(receiver);
        command.execute();
    }
}
