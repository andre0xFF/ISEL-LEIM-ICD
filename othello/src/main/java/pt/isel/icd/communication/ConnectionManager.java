package pt.isel.icd.communication;

import pt.isel.icd.patterns.command.Command;

import java.util.UUID;

public interface ConnectionManager {

    void sendCommand(UUID clientIdentifier, Command<?> command);
    void sendCommand(Command<?> command);

    void addMiddleware(Middleware middleware);
    void removeMiddleware(Middleware middleware);
}
