package pt.isel.icd.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import pt.isel.icd.patterns.command.Command;

import java.io.IOException;
import java.util.UUID;

public interface ConnectionManager {

//    Command<?> readCommand(UUID clientIdentifier) throws IOException, IllegalArgumentException;
    void sendCommand(Command<?> command) throws JsonProcessingException;

    void addMiddleware(Middleware middleware);
    void removeMiddleware(Middleware middleware);
}
