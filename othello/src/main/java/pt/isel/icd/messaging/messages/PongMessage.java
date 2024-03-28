package pt.isel.icd.messaging.messages;

/**
 * A pong message is a message that is sent to the client to check if it is still alive.
 */
public record PongMessage() implements Message {
}
