package pt.isel.icd.ui.auth;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pt.isel.icd.ClientController;
import pt.isel.icd.GameEventListener;
import pt.isel.icd.game.logic.Dot;
import pt.isel.icd.game.logic.PlayerMarker;
import pt.isel.icd.ui.ViewController;
import pt.isel.icd.ui.ViewManager;
import pt.isel.icd.ui.menu.MainMenuController;
import pt.isel.icd.user.logic.User;


public class LoginController implements ViewController, GameEventListener {

    @FXML private TextField fieldUsername;
    @FXML private PasswordField fieldPassword;
    @FXML private Button btnLogin;
    @FXML private Label labelStatus;


    private ClientController clientController;
    private ViewManager viewManager;



    @Override
    public void setClientController(ClientController controller) {

        this.clientController = controller;
        this.clientController.setListener(this);

    }

    @Override
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @Override
    public String getFxmlPath() {
        return "/pt/isel/icd/ui/auth/LoginView.fxml";
    }


    @FXML
    private void onRegisterClicked() {
        viewManager.show(new RegisterController());
    }

    @FXML
    private void onLoginClicked(){
        String username = fieldUsername.getText().trim();
        String password = fieldPassword.getText().trim();
        if(username.isEmpty() || password.isEmpty()) return;

        btnLogin.setDisable(true);
        clientController.authenticateUser(new User(username, password));
    }

    @FXML
    private void onQuitClicked(){
        Platform.exit();
    }

    @Override
    public void onAuthenticated(String username, boolean success){
        Platform.runLater(() ->{
            if(success){
                viewManager.show(new MainMenuController());
            }else {
                labelStatus.setText("Login Failed");
                btnLogin.setDisable(false);
            }
        });
    }
}
