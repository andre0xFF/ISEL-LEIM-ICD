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


        @Override
        public Message deserialize(String content) throws JsonProcessingException {
            return xmlMapper.readValue(content, Message.class);
        }

        @Override
        public String serialize(Message message) throws JsonProcessingException {
            return xmlMapper.writeValueAsString(message);
        }
    }

//    record ErrorMessage(String details) implements Message{
//    }
//
//    record SignInMessage(String username, String password) implements Message{
//
//    }
//
//    record SignUpMessage(String username, String password) implements Message{
//
//    }
//
//    record SignOutMessage() implements Message{
//
//    }
//
//    // server response
//    record GameTurnMessage(GamePlayer currentPlayer, GameBoard board) implements messages.Message{
//
//    }
//
//    //client move request
//    record TokenDropMessage(int gameBoardColumn)implements messages.Message{
//
//    }
//
//    //network.Client move request
//    record TokenDropResultMessage(Boolean isValidTokenDrop) implements messages.Message{
//
//    }
//
//    // server response when game's over to both players
//    record GameOverMessage(Boolean hasWinner, Boolean itsDraw, Player winner) implements messages.Message{
//
//    }
//
//    record AskGameHistory() implements messages.Message{
//
//    }
//
//
//    record GameHistory(int totalVictories, int totalLosses, ArrayList<LocalDateTime, Duration, Player> gamesHistory) implements messages.Message{
//
//    }
//
//    record AskPlayerProfile() implements messages.Message{
//
//    }
//
//    record PlayerProfile(Profile profile) implements messages.Message{
//
//    }
}
