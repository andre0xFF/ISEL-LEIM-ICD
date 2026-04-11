package pt.isel.icd.ui.menu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pt.isel.icd.ClientController;
import pt.isel.icd.GameEventListener;
import pt.isel.icd.game.logic.Dot;
import pt.isel.icd.game.logic.PlayerMarker;
import pt.isel.icd.ui.ViewController;
import pt.isel.icd.ui.ViewManager;
import pt.isel.icd.ui.profile.ProfileController;



public class MainMenuController implements ViewController, GameEventListener {

    @FXML private Label labelWelcome;
    @FXML private Button btnJoinGame;
    @FXML private Button profile;

    private ClientController clientController;
    private ViewManager viewManager;

    @Override
    public void onAuthenticated(String username, boolean success) {}

    @Override
    public void onUserCreated(String username, boolean success) {}

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
        this.clientController = controller;
        this.clientController.setListener(this);
        labelWelcome.setText("Welcome, " + clientController.getUsername());

    }

    @Override
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;

    }

    @Override
    public String getFxmlPath() {
        return "/pt/isel/icd/ui/menu/MainMenuView.fxml";
    }

    @FXML
    public void onProfileClicked(){
        viewManager.show(new ProfileController());
    }



}
