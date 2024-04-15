package pt.isel.icd.communication.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import pt.isel.icd.serialization.XMLSerializer;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class PingCommandTest {

    private final XMLSerializer<Command> XMLSerializer = new XMLSerializer<>();
    private final String serializedPingCommand = "<Command><PingCommand><dateTime>2021-05-18T15:00:00</dateTime></PingCommand></Command>";
    private final LocalDateTime dateTime = LocalDateTime.of(2021, 5, 18, 15, 0, 0);

    @Test
    public void shouldSerializeSuccessfully() throws JsonProcessingException {
        PingCommand pingCommand = new PingCommand(dateTime);

        String actualSerializedPingCommand = XMLSerializer.serialize(pingCommand);

        assertEquals(serializedPingCommand, actualSerializedPingCommand);
    }

    @Test
    public void shouldDeserializeSuccessfully() throws JsonProcessingException {
        var pingCommand = XMLSerializer.deserialize(serializedPingCommand, Command.class);

        assertInstanceOf(PingCommand.class, pingCommand);
        assertInstanceOf(Command.class, pingCommand);
    }
}
