package messages;

import java.time.LocalDateTime;

/**
 * A ping message is a message that is sent to the server to check if it is still alive.
 */
public record PingMessage(LocalDateTime dateTime) implements Message {
    public PingMessage() {
        this(LocalDateTime.now());
    }
}
