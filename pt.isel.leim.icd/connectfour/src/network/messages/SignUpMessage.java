package network.messages;

public record SignUpMessage(String image, String username, char[] password, String nationality, int age) implements Message{
}
