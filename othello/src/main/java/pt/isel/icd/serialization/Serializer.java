package pt.isel.icd.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * A serializer that can serialize and deserialize messages to and from a string format.
 *
 * @param <T> The type of the message.
 */
public interface Serializer {

    /**
     * Deserialize a message from a string.
     *
     * @param content      The string content.
     * @param commandClass The class of the command.
     * @param <K>          The subtype of the command <T>.
     * @return The deserialized message.
     * @throws JsonProcessingException If the string is invalid.
     */
    <T> T deserialize(String content, Class<T> commandClass) throws JsonProcessingException;

    /**
     * Serialize a message to a string.
     *
     * @param message The message to serialize.
     * @param <K>     The subtype of the message <T>.
     * @return The serialized message.
     * @throws JsonProcessingException If the message is invalid.
     */
    <T> String serialize(T message) throws JsonProcessingException;
}
