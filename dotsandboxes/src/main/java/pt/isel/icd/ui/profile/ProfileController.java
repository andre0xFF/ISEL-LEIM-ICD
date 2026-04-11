package pt.isel.icd.ui.profile;

import pt.isel.icd.ClientController;
import pt.isel.icd.GameEventListener;
import pt.isel.icd.game.logic.Dot;
import pt.isel.icd.game.logic.PlayerMarker;
import pt.isel.icd.ui.ViewController;
import pt.isel.icd.ui.ViewManager;

public class ProfileController implements ViewController, GameEventListener {


    //TODO implement Profile


    @Override
    public void onAuthenticated(String username, boolean success) {

    }

    @Override
    public void onUserCreated(String username, boolean success) {

    }

    @Override
    public void onGameJoined(PlayerMarker myMarker) {

    }

    @Override
    public void onLinePlaced(Dot dot1, Dot dot2, String marker, boolean extraTurn) {

    }

    @Override
    public void onGameOver(boolean hasWinner, String winnerMarker, int scoreA, int scoreB) {

    }

    @Override
    public void onGameLeft() {

    }

    @Override
    public void setClientController(ClientController controller) {

    }

    @Override
    public void setViewManager(ViewManager viewManager) {

    }

    @Override
    public String getFxmlPath() {
        return "";
    }
}
