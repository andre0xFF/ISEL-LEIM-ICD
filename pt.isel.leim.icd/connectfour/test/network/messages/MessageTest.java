package network.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import network.GameResult;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class MessageTest {

    private final LocalDateTime dateTime = LocalDateTime.of(2021, 5, 18, 15, 0, 0);
    private final Message.XMLSerializer XMLSerializer = new Message.XMLSerializer();

    private static final String giveSignUpAcceptedMessageContent = "<Message><GiveSignUpAcceptedMessage></GiveSignUpAcceptedMessage></Message>";
    private static final String askSignUpMessageContent = "<Message><AskSignUpMessage><image>%s</image><username>%s</username><password>%s</password><nationality>%s</nationality><age>%s</age></AskSignUpMessage></Message>";


    @Test
    void shouldSerializePingMessageAsXML() throws JsonProcessingException {
        String actualContent = XMLSerializer.serialize(new PingMessage(dateTime));

        assertEquals(PingMessageTest.serializedContent, actualContent);
    }

    @Test
    void shouldSerializeAskLogInMessageAsXML() throws JsonProcessingException {
        char[] pass ={'1','2','3','4'};
        String actualContent = XMLSerializer.serialize(new AskLogInMessage("xpto",pass));

        assertEquals(MessagesTestContent.getAskLogInMessageContent("xpto",pass), actualContent);
    }

    @Test
    void shouldSerializeGiveGamesStatsMessageAsXML() throws JsonProcessingException {
        Time time = new Time(new Date().getTime());

        GiveGamesStatsMessage.GameStat[] stats = {
                new GiveGamesStatsMessage.GameStat("Gamexpto", GameResult.WIN, time.toString()),
                new GiveGamesStatsMessage.GameStat("War2", GameResult.LOSS, time.toString())
        };

        String actualContent = XMLSerializer.serialize(
                new GiveGamesStatsMessage(
                        stats
                )
        );

        assertEquals(MessagesTestContent.getGiveGamesStatsMessageContent(stats), actualContent);
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
