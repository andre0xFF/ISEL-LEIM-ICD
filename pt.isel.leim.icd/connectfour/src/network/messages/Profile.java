package network.messages;

public record Profile(String image, String username, char[] password, String nationality, String age) implements Message {
}
