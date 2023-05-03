package network.messages;

public record GiveUpdatedProfileMessage(String image, String username, char[] password, String nationality, String age) implements Message {
}
