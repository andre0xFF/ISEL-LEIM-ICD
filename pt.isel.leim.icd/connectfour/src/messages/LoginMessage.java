package messages;

/**
 * A login message is a message that is sent to the server to login.
 */
public record LoginMessage(String username, char[] password) implements Message {
}
