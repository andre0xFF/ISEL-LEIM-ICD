package pt.isel.icd.communication;

import pt.isel.icd.communication.commands.Command;
import pt.isel.icd.patterns.observer.Subscriber;

public interface ConnectionSubscriber extends Subscriber<Command> {

    Class<? extends Command>[] commandTypes();
}
