package pt.isel.icd.messaging;

/**
 * A ping message is a message that is sent to the server to check if it is still alive.
 */
public record PingMessage() implements Message {
}
