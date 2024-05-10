package pt.isel.icd.communication;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.UUID;

public interface ConnectionManager {

    void write(UUID clientIdentifier, SimpleSocketCommand<?> simpleSocketCommand) throws JsonProcessingException;
    void write(SimpleSocketCommand<?> command) throws JsonProcessingException;
    void addMiddleware(SimpleSocketMiddleware simpleSocketMiddleware);
    void removeMiddleware(SimpleSocketMiddleware simpleSocketMiddleware);
}
