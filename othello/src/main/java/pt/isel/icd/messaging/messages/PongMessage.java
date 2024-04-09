package pt.isel.icd.messaging.messages;

import java.time.LocalDateTime;

/**
 * A pong message is a message that is sent to the client to check if it is still alive.
 */
public record PongMessage(LocalDateTime dateTime) implements Message {
}
