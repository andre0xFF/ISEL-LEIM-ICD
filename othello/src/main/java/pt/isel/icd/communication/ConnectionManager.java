package pt.isel.icd.communication;

import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;

import java.util.UUID;

public interface ConnectionManager {

    void sendCommand(UUID clientIdentifier, Command<?> command);
    void sendCommand(Command<?> command);

//     void addMiddleware(Middleware<? extends Receiver> middleware);
//     void removeMiddleware(Middleware<? extends Receiver> middleware);

}
