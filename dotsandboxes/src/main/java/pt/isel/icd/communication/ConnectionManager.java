package pt.isel.icd.communication;

import java.util.UUID;

public interface ConnectionManager {
    void write(UUID clientIdentifier, SimpleSocketCommand<?> command);
    void write(SimpleSocketCommand<?> command);
}
