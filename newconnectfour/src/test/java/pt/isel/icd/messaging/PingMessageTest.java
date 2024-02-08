package pt.isel.icd.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import pt.isel.icd.serialization.XMLSerializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class PingMessageTest {
    
    private final XMLSerializer<Message> XMLSerializer = new XMLSerializer<>();
    private final String serializedPingMessage = "<Message><PingMessage/></Message>";
    
    @Test
    public void shouldSerializeSuccessfully() throws JsonProcessingException {
        PingMessage pingMessage = new PingMessage();
        
        String actualSerializedPingMessage = XMLSerializer.serialize(pingMessage);
        
        assertEquals(serializedPingMessage, actualSerializedPingMessage);
    }
    
    @Test
    public void shouldDeserializeSuccessfully() throws JsonProcessingException {
        var pingMessage = XMLSerializer.deserialize(serializedPingMessage, Message.class);
        
        assertInstanceOf(PingMessage.class, pingMessage);
        assertInstanceOf(Message.class, pingMessage);
    }
}
