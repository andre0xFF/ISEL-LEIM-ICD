package pt.isel.icd.communication;

import pt.isel.icd.patterns.chainofresponsability.Handler;
import pt.isel.icd.patterns.command.Command;

public interface Middleware extends Handler<Command<?>> {

}
