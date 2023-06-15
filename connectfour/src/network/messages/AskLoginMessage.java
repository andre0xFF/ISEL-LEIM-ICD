package network.messages;

/**
 * A login message is a message that is sent to the server to login.
 */
public record AskLoginMessage(String username, char[] password) implements Message {

}
