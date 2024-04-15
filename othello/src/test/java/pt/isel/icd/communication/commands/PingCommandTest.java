package pt.isel.icd.messaging.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import pt.isel.icd.serialization.XMLSerializer;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class PingCommandTest {

    private final XMLSerializer<Command> XMLSerializer = new XMLSerializer<>();
    private final String serializedPingMessage = "<Message><PingMessage><dateTime>2021-05-18T15:00:00</dateTime></PingMessage></Message>";
    private final LocalDateTime dateTime = LocalDateTime.of(2021, 5, 18, 15, 0, 0);

    @Test
    public void shouldSerializeSuccessfully() throws JsonProcessingException {
        PingCommand pingMessage = new PingCommand(dateTime);

        String actualSerializedPingMessage = XMLSerializer.serialize(pingMessage);

        assertEquals(serializedPingMessage, actualSerializedPingMessage);
    }

    @Test
    public void shouldDeserializeSuccessfully() throws JsonProcessingException {
        var pingMessage = XMLSerializer.deserialize(serializedPingMessage, Command.class);

        assertInstanceOf(PingCommand.class, pingMessage);
        assertInstanceOf(Command.class, pingMessage);
    }
}
