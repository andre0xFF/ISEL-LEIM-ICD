package pt.isel.icd.communication;

import pt.isel.icd.patterns.chainofresponsability.Handler;
import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;

public interface Middleware<T extends Receiver> {

    void handle(T receiver, Command<?> command);
}
