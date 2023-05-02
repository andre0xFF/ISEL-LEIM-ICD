package network.messages;

import java.sql.Time;

public record GameStat(String gameid, String gameresult, Time time) {
}
