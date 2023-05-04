package network.messages;

import java.sql.Time;

public record GameStat(String id, String result, Time time) {
}
