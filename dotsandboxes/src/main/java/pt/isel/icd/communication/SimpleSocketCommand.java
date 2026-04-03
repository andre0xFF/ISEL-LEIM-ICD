package pt.isel.icd.communication;

import java.util.UUID;
import pt.isel.icd.serialization.CommandSerializer;

/**
 * A command that can be sent/received over a SimpleSocket.
 * Serialization is done via DOM XML, not data-binding.
 */
public interface SimpleSocketCommand<
    T
> extends CommandSerializer.XmlSerializable {
    void setReceiver(T receiver);
    void execute();

    UUID socketId();

    void socketId(UUID socketId);

    /**
     * Returns the command name used in XML serialization (the element name).
     */
    String commandName();

    default boolean requiresAuthentication() {
        return true;
    }
}
