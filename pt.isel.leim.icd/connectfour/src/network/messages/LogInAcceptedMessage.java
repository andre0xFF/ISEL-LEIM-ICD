package network.messages;

public record LogInAcceptedMessage(Profile profile, GamesStats gamesStats) implements Message{
}
