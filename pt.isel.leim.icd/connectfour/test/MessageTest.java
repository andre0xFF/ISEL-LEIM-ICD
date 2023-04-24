import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {
    public static final String pingMessageContent = "<Message type=\"PingMessage\"><dateTime>2021-05-18T15:00:00</dateTime></Message>";
    public static String pongMessageContent = "<Message type=\"PongMessage\"><dateTime>2021-05-18T15:00:00</dateTime></Message>";
    private final LocalDateTime dateTime = LocalDateTime.of(2021, 5, 18, 15, 0, 0);
    private final Message.Serializer serializer = new Message.Serializer();

    @org.junit.jupiter.api.Test
    void shouldConstructPingMessage() {
        Message.PingMessage message = new Message.PingMessage(dateTime);

        assertEquals(dateTime, message.dateTime());
    }

    @org.junit.jupiter.api.Test
    void shouldSerializePingMessageAsXML() throws JsonProcessingException {
        String actualContent = serializer.serialize(new Message.PingMessage(dateTime));

        assertEquals(pingMessageContent, actualContent);
    }

    @org.junit.jupiter.api.Test
    void shouldDeserializePingMessageFromXML() throws JsonProcessingException {
        Message.PingMessage actualMessage = (Message.PingMessage) serializer.deserialize(pingMessageContent);

        assertEquals(dateTime, actualMessage.dateTime());
    }

    @org.junit.jupiter.api.Test
    void shouldCastPingMessage() throws JsonProcessingException {
        Message deserializedMessaged = serializer.deserialize(pingMessageContent);

        Message.PingMessage actualMessage = (Message.PingMessage) deserializedMessaged;

        assertEquals(dateTime, actualMessage.dateTime());
    }

    @org.junit.jupiter.api.Test
    void shouldCastPingMessageUsingReflection() throws JsonProcessingException {
        Message deserializedMessaged = serializer.deserialize(pingMessageContent);

        var targetClass = Message.PingMessage.class;

        assertDoesNotThrow(() -> call(targetClass.cast(deserializedMessaged)));
    }

    private void call(Message.PingMessage message) {}

    private void test() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // Get the class object for the class you want to instantiate
        Class<Message.PingMessage> clazz = Message.PingMessage.class;

        // Get the constructor for the class
        Constructor<Message.PingMessage> constructor = clazz.getConstructor();

        // Instantiate an object of the class using the constructor
        Message.PingMessage obj = constructor.newInstance();
    }
}
