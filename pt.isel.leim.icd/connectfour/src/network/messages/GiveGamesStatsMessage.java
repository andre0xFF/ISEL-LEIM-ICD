package network.messages;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public record GiveGamesStatsMessage(
        @JacksonXmlElementWrapper(localName = "GamesStats")
        @JacksonXmlProperty(localName = "GameStat")
        GameStat[] GamesStats
) implements Message {
}
