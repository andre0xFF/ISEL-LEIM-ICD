package network.messages;

public record GiveGamesStatsMessage(GameStat[] GameStats) implements Message{
}
