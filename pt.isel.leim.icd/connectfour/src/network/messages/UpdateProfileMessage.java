package network.messages;

public record UpdateProfileMessage(String image, String username, char[] password, String nationality, int age) implements Message{
}
