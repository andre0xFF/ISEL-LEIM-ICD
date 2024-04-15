package pt.isel.icd.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A serializer that can serialize and deserialize messages to and from XML.
 * @param <T> The type of the message.
 */
public class XMLSerializer<T> implements Serializer<T> {
    private final XmlMapper xmlMapper = new XmlMapper();

    public XMLSerializer() {
        registerDateTimeModule();
        registerColorModule();
    }

    private void registerDateTimeModule() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        JavaTimeModule module = (JavaTimeModule) new JavaTimeModule().addSerializer(
                LocalDateTime.class,
                new LocalDateTimeSerializer(formatter)
        );

        xmlMapper.registerModule(module);
    }

    private void registerColorModule() {
        xmlMapper.registerModule(
                new SimpleModule()
                        .addSerializer(Color.class, new ColorSerializer())
                        .addDeserializer(Color.class, new ColorDeserializer())
        );
    }

    /**
     * Deserialize a message from XML.
     *
     * @param content   The XML content.
     * @param commandClass The class of the message.
     * @return The deserialized message.
     * @param <K> The subtype of the message <T>.
     * @throws JsonProcessingException If the XML is invalid.
     */
    @Override
    public <K extends T> K deserialize(String content, Class<T> commandClass) throws JsonProcessingException {
        return ((K) xmlMapper.readValue(content, commandClass));
    }

    /**
     * Serialize a message to XML.
     * @param message The message to serialize.
     * @return The serialized message.
     * @param <K> The subtype of the message <T>.
     * @throws JsonProcessingException If the message cannot be serialized.
     */
    @Override
    public <K extends T> String serialize(K message) throws JsonProcessingException {
        return xmlMapper.writeValueAsString(message);
    }
}
