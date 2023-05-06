package network.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import network.GameResult;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class MessageTest {

    private final LocalDateTime dateTime = LocalDateTime.of(2021, 5, 18, 15, 0, 0);
    private final Message.XMLSerializer XMLSerializer = new Message.XMLSerializer();

    private static final String giveSignUpAcceptedMessageContent = "<Message><GiveSignUpAcceptedMessage></GiveSignUpAcceptedMessage></Message>";
    private static final String askSignUpMessageContent = "<Message><AskSignUpMessage><image>%s</image><username>%s</username><password>%s</password><nationality>%s</nationality><age>%s</age></AskSignUpMessage></Message>";


    @Test
    void shouldSerializeOnLossMessageAsXML() throws JsonProcessingException {
        String actualContent = XMLSerializer.serialize(new OnLossMessage());

        assertEquals("<Message><OnLossMessage/></Message>", actualContent);
    }

    @Test
    void shouldSerializeOnWinMessageAsXML() throws JsonProcessingException {
        String actualContent = XMLSerializer.serialize(new OnWinMessage());

        assertEquals("<Message><OnWinMessage/></Message>", actualContent);
    }

    @Test
    void shouldSerializeOnPlayTurnMessageAsXML() throws JsonProcessingException {
        String actualContent = XMLSerializer.serialize(new OnPlayTurnMessage());

        assertEquals("<Message><OnPlayTurnMessage/></Message>", actualContent);
    }

    @Test
    void shouldSerializeOnWaitTurnMessageAsXML() throws JsonProcessingException {
        String actualContent = XMLSerializer.serialize(new OnWaitTurnMessage());

        assertEquals("<Message><OnWaitTurnMessage/></Message>", actualContent);
    }

    @Test
    void shouldSerializePingMessageAsXML() throws JsonProcessingException {
        String actualContent = XMLSerializer.serialize(new PingMessage(dateTime));

        assertEquals(PingMessageTest.serializedContent, actualContent);
    }

    @Test
    void shouldSerializeAskLoginMessageAsXML() throws JsonProcessingException {
        char[] pass = {'1', '2', '3', '4'};
        String actualContent = XMLSerializer.serialize(new AskLoginMessage("xpto", pass));

        String expectedContent = "<Message><AskLoginMessage><username>xpto</username><password>1234</password></AskLoginMessage></Message>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    void shouldSerializeGiveGamesStatsMessageAsXML() throws JsonProcessingException {
        GiveGamesStatsMessage.GameStat[] stats = {
                new GiveGamesStatsMessage.GameStat(
                        "Gamexpto",
                        GameResult.WIN,
                        LocalDateTime.of(2021, 5, 18, 15, 0, 0)
                ),
                new GiveGamesStatsMessage.GameStat(
                        "War2",
                        GameResult.LOSS,
                        LocalDateTime.of(2021, 5, 19, 15, 0, 0)
                )
        };

        String actualContent = XMLSerializer.serialize(
                new GiveGamesStatsMessage(
                        stats
                )
        );

        String expectedContent = "<Message><GiveGamesStatsMessage><GamesStats><GameStat><id>Gamexpto</id><result>WIN</result><dateTime>2021-05-18T15:00:00</dateTime></GameStat><GameStat><id>War2</id><result>LOSS</result><dateTime>2021-05-19T15:00:00</dateTime></GameStat></GamesStats></GiveGamesStatsMessage></Message>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    void shouldDeserializePingMessageFromXML() throws JsonProcessingException {
        PingMessage actualMessage = (PingMessage) XMLSerializer.deserialize(PingMessageTest.serializedContent);

        assertEquals(dateTime, actualMessage.dateTime());
    }

    @Test
    void shouldCastPingMessage() throws JsonProcessingException {
        Message deserializedMessaged = XMLSerializer.deserialize(PingMessageTest.serializedContent);

        PingMessage actualMessage = (PingMessage) deserializedMessaged;

        assertEquals(dateTime, actualMessage.dateTime());
    }

    @Test
    void shouldCastPingMessageUsingReflection() throws JsonProcessingException {
        Message deserializedMessaged = XMLSerializer.deserialize(PingMessageTest.serializedContent);

        var targetClass = PingMessage.class;

        assertDoesNotThrow(() -> call(targetClass.cast(deserializedMessaged)));
    }

    void call(PingMessage message) {}

    @Test
    void shouldConstructPingMessageUsingReflection() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // Get the class object for the class you want to instantiate
        Class<PingMessage> clazz = PingMessage.class;

        // Get the constructor for the class
        Constructor<PingMessage> constructor = clazz.getConstructor();

        // Instantiate an object of the class using the constructor
        PingMessage obj = constructor.newInstance();
    }
}
