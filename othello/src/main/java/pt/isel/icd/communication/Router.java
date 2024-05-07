package pt.isel.icd.communication;


import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Invoker;
import pt.isel.icd.patterns.command.Receiver;

import java.util.HashMap;

public class Router implements Invoker<Receiver> {

    private final HashMap<Class<? extends Command<? extends Receiver>>, Receiver> controllers = new HashMap<>();
    private Command<Receiver> command;

    public void addReceiver(Class<? extends Command<? extends Receiver>> commandType, Receiver controller) {
        controllers.put(commandType, controller);
    }

    public void removeReceiver(Class<? extends Command<? extends Receiver>> commandType) {
        controllers.remove(commandType);
    }

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

        command.setReceiver(receiver);
        command.execute();
    }
}
