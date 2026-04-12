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
import pt.isel.icd.user.logic.User;

public class RegisterController implements ViewController, GameEventListener {

    @FXML private TextField fieldUsername;
    @FXML private PasswordField fieldPassword;
    @FXML private PasswordField fieldConfirmPassword;
    @FXML private Button btnRegister;
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
        return "/pt/isel/icd/ui/auth/RegisterView.fxml";
    }

    @FXML
    private void onRegisterClicked() {
        String username = fieldUsername.getText().trim();
        String password = fieldPassword.getText();
        String confirm = fieldConfirmPassword.getText();

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            labelStatus.setText("All fields are required.");
            return;
        }
        if (username.length() < 3 || username.length() > 20) {
            labelStatus.setText("Username must be 3–20 characters.");
            return;
        }
        if (password.length() < 8 || password.length() > 20) {
            labelStatus.setText("Password must be 8–20 characters.");
            return;
        }
        if (!password.equals(confirm)) {
            labelStatus.setText("Passwords do not match.");
            return;
        }

        btnRegister.setDisable(true);
        clientController.createUser(new User(username, password));
    }

    @FXML
    private void onBackClicked() {
        viewManager.show(new LoginController());
    }

    @Override
    public void onUserCreated(String username, boolean success) {
        Platform.runLater(() -> {
            if (success) {
                viewManager.show(new LoginController());
            } else {
                labelStatus.setText("Registration failed. Username may already exist.");
                btnRegister.setDisable(false);
            }
        });
    }
}