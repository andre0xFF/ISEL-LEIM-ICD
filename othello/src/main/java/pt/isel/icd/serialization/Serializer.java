package pt.isel.icd.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * A serializer that can serialize and deserialize objects to and from a string format.
 *
 * @param <T> The type of the message.
 */
public interface Serializer {

    /**
     * Deserialize an XML string to an object.
     *
     * @param content The XML string content.
     * @param type The class of the object.
     * @return The deserialized object.
     * @param <T> The type of the object <T>.
     * @throws JsonProcessingException If the XML string is invalid.
     */
    <T> T deserialize(String content, Class<T> type) throws JsonProcessingException;

    /**
     * Serialize an object to an XML string.
     *
     * @param object The object to serialize.
     * @return The serialized XML string.
     * @param <T> The type of the object <T>.
     * @throws JsonProcessingException If the object is invalid.
     */
    <T> String serialize(T object) throws JsonProcessingException;
}
