import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.Duration;
import java.util.Date;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes(
        {
                @JsonSubTypes.Type(value = Message.PingMessage.class, name = "PingMessage"),
                @JsonSubTypes.Type(value = Message.PongMessage.class, name = "PongMessage")
        }
)
@JsonRootName("Message")
public interface Message {

    /**
     * A serializer can serialize and deserialize messages.
     */
    class Serializer {
        private final XmlMapper xmlMapper = new XmlMapper();

        public Serializer() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

            JavaTimeModule module = (JavaTimeModule) new JavaTimeModule().addSerializer(
                    LocalDateTime.class,
                    new LocalDateTimeSerializer(formatter)
            );

            xmlMapper.registerModule(module);
        }

        public Message deserialize(String content) throws JsonProcessingException {
            return xmlMapper.readValue(content, Message.class);
        }

        public String serialize(Message message) throws JsonProcessingException {
            return xmlMapper.writeValueAsString(message);
        }
    }

    /**
     * A ping message is a message that is sent to the server to check if it is still alive.
     */
    record PingMessage(LocalDateTime dateTime) implements Message {
        public PingMessage() {
            this(LocalDateTime.now());
        }
    }

    /**
     * A pong message is a message that is sent to the client to check if it is still alive.
     */
    record PongMessage(LocalDateTime dateTime) implements Message {
        public PongMessage() {
            this(LocalDateTime.now());
        }
    }

    record ErrorMessage(String details) implements Message{
    }

    record SignInMessage(String username, String password) implements Message{

    }

    record SignUpMessage(String username, String password) implements Message{

    }

    record SignOutMessage() implements Message{

    }

    // server response
//    record GameTurnMessage(GamePlayer currentPlayer, GameBoard board) implements Message{
//
//    }

    //client move request
    record TokenDropMessage(int gameBoardColumn)implements Message{

    }

    //Client move request
    record TokenDropResultMessage(Boolean isValidTokenDrop) implements Message{

    }

    // server response when game's over to both players
//    record GameOverMessage(Boolean hasWinner, Boolean itsDraw, Player winner) implements Message{

//    }

    record AskGameHistory() implements Message{

    }


//    record GameHistory(int totalVictories, int totalLosses, ArrayList<LocalDateTime, Duration, Player> gamesHistory) implements Message{
//
//    }

    record AskPlayerProfile() implements Message{

    }

//    record PlayerProfile(Profile profile) implements Message{
//
//    }


}
