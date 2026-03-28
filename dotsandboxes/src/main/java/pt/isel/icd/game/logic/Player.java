package pt.isel.icd.game.logic;

import java.util.Objects;

/**
 * Represents a player in the game.
 * Each player has a marker (A or B) and a score (number of boxes claimed).
 */
public class Player {

    private final PlayerMarker marker;
    private int score;

    public Player(PlayerMarker marker) {
        this.marker = marker;
        this.score = 0;
    }

    public PlayerMarker marker() {
        return marker;
    }

    public int score() {
        return score;
    }

    public void incrementScore() {
        score++;
    }

    public void incrementScore(int amount) {
        score += amount;
    }

    public void resetScore() {
        score = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return marker == player.marker;
    }

    @Override
    public int hashCode() {
        return Objects.hash(marker);
    }

    @Override
    public String toString() {
        return "Player(" + marker + ", score=" + score + ")";
    }
}
