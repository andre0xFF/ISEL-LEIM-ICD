package network.messages;

/**
 * A login message is a message that is sent to the server to login.
 */
public record LogInMessage(String username, char[] password) implements Message {
}
