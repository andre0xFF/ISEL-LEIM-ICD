package network.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class MessageTest {

    public final static String messageEmptyContent = "<Message></Message>";
    private final LocalDateTime dateTime = LocalDateTime.of(2021, 5, 18, 15, 0, 0);
    private final Message.XMLSerializer XMLSerializer = new Message.XMLSerializer();

 private final Time time = new Time(new Date().getTime());


    @Test
    void shouldSerializePingMessageAsXML() throws JsonProcessingException {
        String actualContent = XMLSerializer.serialize(new PingMessage(dateTime));

        assertEquals(PingMessageTest.serializedContent, actualContent);
    }

    @Test
    void shouldSerializeAskLogInMessageAsXML() throws JsonProcessingException {
        String actualContent = XMLSerializer.serialize(new AskLogInMessage("xpto", new char[]{'1','2','3','4'}));

        assertEquals("<Message><AskLogInMessage><username>xpto</username><password>1234</password></AskLogInMessage></Message>", actualContent);
    }

    @Test
    void shouldSerializeGiveGamesStatsMessageAsXML() throws JsonProcessingException {
        String actualContent = XMLSerializer.serialize(new GiveGamesStatsMessage(new GameStat[]{new GameStat("Gamexpto", "win", time), new GameStat("War2", "Loss", time)}));

        assertEquals(String.format("<Message><GiveGamesStatsMessage><GamesStats><GameStat><id>Gamexpto</id><result>win</result><time>%s</time></GameStat><GameStat><id>War2</id><result>Loss</result><time>%s</time></GameStat></GamesStats></GiveGamesStatsMessage></Message>", time, time), actualContent);
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
