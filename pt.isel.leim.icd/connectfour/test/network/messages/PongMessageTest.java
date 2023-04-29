package network.messages;

import network.messages.PongMessage;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PongMessageTest {
    public static String serializedContent = "<Message><PongMessage><dateTime>2021-05-18T15:00:00</dateTime></PongMessage></Message>";

    private final LocalDateTime dateTime = LocalDateTime.of(2021, 5, 18, 15, 0, 0);

    @Test
    void shouldConstructPongMessage() {
        PongMessage message = new PongMessage(dateTime);

        assertEquals(dateTime, message.dateTime());
    }
}
