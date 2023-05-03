package network.messages;

public record AskUpdateProfileMessage(String image, String username, char[] password, String nationality, int age) implements Message{
}
