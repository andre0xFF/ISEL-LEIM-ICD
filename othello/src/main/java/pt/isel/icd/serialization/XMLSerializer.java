package pt.isel.icd.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A serializer that can serialize and deserialize messages to and from XML.
 */
public class XMLSerializer implements Serializer {
    private final XmlMapper xmlMapper = new XmlMapper();

    public XMLSerializer() {
        registerDateTimeModule();
        registerColorModule();
    }

    private void registerDateTimeModule() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
//
//        JavaTimeModule module = (JavaTimeModule) new JavaTimeModule().addSerializer(
//                LocalDateTime.class,
//                new LocalDateTimeSerializer(formatter)
//        );
//
//        xmlMapper.registerModule(module);

        xmlMapper.registerModule(
                new JavaTimeModule()
                        .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME))
                        .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME))
        );

        xmlMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    private void registerColorModule() {
        xmlMapper.registerModule(
                new SimpleModule()
                        .addSerializer(Color.class, new ColorSerializer())
                        .addDeserializer(Color.class, new ColorDeserializer())
        );
    }

    private void registerBufferedImageModule() {
        xmlMapper.registerModule(
                new SimpleModule()
                        .addSerializer(BufferedImage.class, new BufferedImageSerializer())
                        .addDeserializer(BufferedImage.class, new BufferedImageDeserializer())
        );
    }


    /**
     * Deserialize an XML string to an object.
     *
     * @param content The XML string content.
     * @param type    The class of the object.
     * @param <T>     The type of the object <T>.
     * @return The deserialized object.
     * @throws JsonProcessingException If the XML string is invalid.
     */
    @Override
    public <T> T deserialize(String content, Class<T> type) throws JsonProcessingException {
        return xmlMapper.readValue(content, type);
    }

    @Override
    public <T> T deserialize(File file, Class<T> type) throws IOException {
        return xmlMapper.readValue(file, type);
    }

    /**
     * Serialize an object to an XML string.
     *
     * @param object The object to serialize.
     * @param <T>    The type of the object <T>.
     * @return The serialized XML string.
     * @throws JsonProcessingException If the object is invalid.
     */
    @Override
    public <T> String serialize(T object) throws JsonProcessingException {
        return xmlMapper.writeValueAsString(object);
    }

    @Override
    public <T> void serialize(File file, T object) throws IOException {
        xmlMapper.writeValue(file, object);
    }
}
