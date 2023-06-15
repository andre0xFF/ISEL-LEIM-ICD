package network.messages;

public record AskSignUpMessage(String image, String username, char[] password, String nationality, int age) implements Message{
}
