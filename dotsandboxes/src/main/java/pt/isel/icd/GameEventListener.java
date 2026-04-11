package pt.isel.icd;

import pt.isel.icd.game.logic.Dot;
import pt.isel.icd.game.logic.PlayerMarker;

public interface GameEventListener {
    void onAuthenticated(String username, boolean success);
    void onUserCreated(String username, boolean success);
    void onGameJoined(PlayerMarker myMarker);
    void onLinePlaced(Dot dot1, Dot dot2, String marker, boolean extraTurn);
    void onGameOver(boolean hasWinner, String winnerMarker, int scoreA, int scoreB);
    void onGameLeft();
}