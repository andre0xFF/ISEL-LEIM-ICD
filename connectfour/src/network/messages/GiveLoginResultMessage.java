package network.messages;

public record GiveLoginResultMessage(boolean authenticated) implements Message {
}
