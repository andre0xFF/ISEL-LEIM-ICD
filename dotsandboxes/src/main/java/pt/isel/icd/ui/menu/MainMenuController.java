package pt.isel.icd.ui.menu;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pt.isel.icd.ClientController;
import pt.isel.icd.GameEventListener;
import pt.isel.icd.game.logic.Dot;
import pt.isel.icd.game.logic.PlayerMarker;
import pt.isel.icd.ui.ViewController;
import pt.isel.icd.ui.ViewManager;
import pt.isel.icd.ui.game.GameController;
import pt.isel.icd.ui.profile.ProfileController;



public class MainMenuController implements ViewController, GameEventListener {

    @FXML private Label labelWelcome;

    private ClientController clientController;
    private ViewManager viewManager;


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

    @FXML
    public void onQuitClicked(){
        Platform.exit();
    }

    @FXML
    public void onPlayClicked(){
        viewManager.show(new GameController());
    }
}
