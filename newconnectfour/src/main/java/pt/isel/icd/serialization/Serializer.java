package pt.isel.icd.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * A serializer that can serialize and deserialize messages to and from a string format.
 * @param <T> The type of the message.
 */
public interface Serializer<T> {

    /**
     * Deserialize a message from a string.
     * @param content The string content.
     * @param messageClass The class of the message.
     * @return The deserialized message.
     * @param <K> The subtype of the message <T>.
     * @throws JsonProcessingException If the string is invalid.
     */
    <K extends T> K deserialize(String content, Class<K> messageClass) throws JsonProcessingException;

    /**
     * Serialize a message to a string.
     * @param message The message to serialize.
     * @return The serialized message.
     * @param <K> The subtype of the message <T>.
     * @throws JsonProcessingException If the message is invalid.
     */
    <K extends T> String serialize(K message) throws JsonProcessingException;
}
