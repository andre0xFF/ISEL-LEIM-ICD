package network.messages;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Date;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


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
    void shouldSerializeLogInAcceptedMessageAsXML() throws JsonProcessingException {
        String actualContent = XMLSerializer.serialize(new LogInAcceptedMessage(
                new Profile("image","Daniel", new char[]{'1', '2', '3', '4'},"PT", "1337"),
                new GamesStats(new GameStat[]{new GameStat("123", "Win", time)})));

        assertEquals("<LogInAcceptedMessage><profile><image>asdasda</image><username>xpto</username><password>1234</password><nationality>PT</nationality><age>1231</age></profile><gamestats><gamestat><gameid>12312</gameid><gameresult>Win</gameresult><time>00:30:00</time></gamestat></gamestats></LogInAcceptedMessage>", actualContent);

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
