package pt.isel.icd;

import pt.isel.icd.game.logic.Dot;
import pt.isel.icd.game.logic.PlayerMarker;
import pt.isel.icd.user.logic.Profile;

public interface GameEventListener {
    default void onAuthenticated(String username, boolean success){};
    default void onUserCreated(String username, boolean success){};
    default void onGameJoined(PlayerMarker myMarker){};
    default void onLinePlaced(Dot dot1, Dot dot2, String marker, boolean extraTurn){};
    default void onGameOver(boolean hasWinner, String winnerMarker, int scoreA, int scoreB){};
    default void onGameLeft(){};
    default void onProfileRead(Profile profile, boolean hasProfile){}
}