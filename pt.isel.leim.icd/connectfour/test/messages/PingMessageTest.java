package messages;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PingMessageTest {

    public static final String pingMessageContent = "<Message type=\"PingMessage\"><dateTime>2021-05-18T15:00:00</dateTime></Message>";

    public static final String pingMessageContent2 = "<Message><PingMessage><dateTime>2021-05-18T15:00:00</dateTime></PingMessage></Message>";
    private final LocalDateTime dateTime = LocalDateTime.of(2021, 5, 18, 15, 0, 0);

    @Test
    void shouldConstructPingMessage() {
        PingMessage message = new PingMessage(dateTime);

        assertEquals(dateTime, message.dateTime());
    }
}
