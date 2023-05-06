package network.messages;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A message is a data transfer object that is sent over the network.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT
)
@JsonSubTypes(
        {
                @JsonSubTypes.Type(value = PingMessage.class, name = "PingMessage"),
                @JsonSubTypes.Type(value = PongMessage.class, name = "PongMessage"),
                @JsonSubTypes.Type(value = AskLogInMessage.class, name = "AskLogInMessage"),
                @JsonSubTypes.Type(value = DropTokenMessage.class, name = "DropTokenMessage"),

                @JsonSubTypes.Type(value = AskGamesStatsMessage.class, name = "AskGamesStatsMessage"),
                @JsonSubTypes.Type(value = PlayTurnMessage.class, name = "PlayTurnMessage"),
                @JsonSubTypes.Type(value = AskSignUpMessage.class, name = "AskSignUpMessage"),

                @JsonSubTypes.Type(value = GiveLogInAcceptedMessage.class, name = "GiveLogInAcceptedMessage"),
                @JsonSubTypes.Type(value = AskUpdateProfileMessage.class, name = "AskUpdateProfileMessage"),
                @JsonSubTypes.Type(value = GiveGamesStatsMessage.class, name = "GiveGamesStatsMessage"),
                @JsonSubTypes.Type(value = GameOverMessage.class, name = "GameOverMessage"),
                @JsonSubTypes.Type(value = AskQueueGameMessage.class, name = "AskQueueGameMessage"),
                @JsonSubTypes.Type(value = GiveOpponentFoundMessage.class, name = "GiveOpponentFoundMessage")
        }
)
@JsonRootName("Message")
public interface Message {

    /**
     * A serializer can serialize and deserialize messages.
     */
    interface Serializer {

        Message deserialize(String content) throws JsonProcessingException;

        String serialize(Message message) throws JsonProcessingException;
    }

    /**
     * A serializer that uses XML.
     */
    class XMLSerializer implements Serializer {
        private final XmlMapper xmlMapper = new XmlMapper();

        public XMLSerializer() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

            JavaTimeModule module = (JavaTimeModule) new JavaTimeModule().addSerializer(
                    LocalDateTime.class,
                    new LocalDateTimeSerializer(formatter)
            );

            xmlMapper.registerModule(module);
        }


        /**
         * Deserialize a message from XML.
         *
         * @param content The XML content.
         * @return The deserialized message.
         * @throws JsonProcessingException If the XML is invalid.
         */
        @Override
        public Message deserialize(String content) throws JsonProcessingException {
            return xmlMapper.readValue(content, Message.class);
        }

        /**
         * Serialize a message to XML.
         *
         * @param message The message to serialize.
         * @return The serialized message.
         * @throws JsonProcessingException If the message cannot be serialized.
         */
        @Override
        public String serialize(Message message) throws JsonProcessingException {
            return xmlMapper.writeValueAsString(message);
        }
    }
}
