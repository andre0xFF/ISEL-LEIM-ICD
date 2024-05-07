package pt.isel.icd.communication;


import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Invoker;
import pt.isel.icd.patterns.command.Receiver;

import java.util.ArrayList;
import java.util.HashMap;

public class Router implements Invoker<Receiver> {

    private final HashMap<Class<? extends Command<? extends Receiver>>, Receiver> controllers = new HashMap<>();
    private final ArrayList<Middleware<? extends Receiver>> middlewares = new ArrayList<>();
    private Command<Receiver> command;

    public void addReceiver(Class<? extends Command<? extends Receiver>> commandType, Receiver controller) {
        controllers.put(commandType, controller);
    }

    public void removeReceiver(Class<? extends Command<? extends Receiver>> commandType) {
        controllers.remove(commandType);
    }

//    public void addMiddleware(Middleware<? extends Receiver> middleware) {
//        middlewares.add(middleware);
//    }
//
//    public void removeMiddleware(Middleware<? extends Receiver> middleware) {
//        middlewares.remove(middleware);
//    }

    public void route(Command<Receiver> newCommand) {
        setCommand(newCommand);
        executeCommand();
    }

    @Override
    public void setCommand(Command<Receiver> newCommand) {
        command = newCommand;
    }

    @Override
    public void executeCommand() {
        Class<?> commandType = command.getClass();
        Receiver receiver = controllers.get(commandType);

        if (receiver == null) {
            return;
        }

//        for (Middleware<? extends Receiver> middleware : middlewares) {
//            middleware.handle(receiver, command);
//        }

        command.setReceiver(receiver);
        command.execute();
    }
}
