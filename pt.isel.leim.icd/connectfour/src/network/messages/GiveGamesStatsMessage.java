package network.messages;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import network.GameResult;

import java.time.LocalDateTime;

public record GiveGamesStatsMessage(
        @JacksonXmlElementWrapper(localName = "GamesStats")
        @JacksonXmlProperty(localName = "GameStat")
        GameStat[] GamesStats
) implements Message {

        public record GameStat(String id, Enum<GameResult> result, LocalDateTime dateTime) {
        }
}
