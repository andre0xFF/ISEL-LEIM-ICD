package pt.isel.icd.ui.profile;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pt.isel.icd.ClientController;
import pt.isel.icd.GameEventListener;
import pt.isel.icd.communication.Client;
import pt.isel.icd.game.logic.Dot;
import pt.isel.icd.game.logic.PlayerMarker;
import pt.isel.icd.ui.ViewController;
import pt.isel.icd.ui.ViewManager;
import pt.isel.icd.ui.menu.MainMenuController;
import pt.isel.icd.user.logic.Profile;


public class ProfileController implements ViewController, GameEventListener {

    private ViewManager viewManager;
    private ClientController clientController;

    @FXML private Label lblUsername;
    @FXML private TextField fieldNationality;
    @FXML private TextField fieldAge;
    @FXML private TextField fieldPhoto;
    @FXML private Label lblWins;
    @FXML private Label lblLosses;
    @FXML private Button btnEdit;
    @FXML private Button btnSave;
    @FXML private Label lblStatus;

    //TODO implement Profile




    @Override
    public void setClientController(ClientController controller) {
        this.clientController = controller;
        this.clientController.setListener(this);
        clientController.readUserProfile();
    }

    @Override
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @Override
    public String getFxmlPath() {
        return "/pt/isel/icd/ui/profile/ProfileView.fxml";
    }

    @Override
    public void onProfileRead(Profile profile, boolean hasProfile){
        Platform.runLater(() ->{

            if(hasProfile){
                lblUsername.setText(profile.username());
                fieldNationality.setText(profile.nationality());
                fieldAge.setText(String.valueOf(profile.age()));
                fieldPhoto.setText(profile.photo());
                lblWins.setText(String.valueOf(profile.wins()));
                lblLosses.setText(String.valueOf(profile.losses()));
            }else{
                lblUsername.setText(clientController.getUsername());
                lblStatus.setText("No profile yet. Click Edit to create one");
            }
        });
    }

    @FXML
    private void onEditClicked(){
        fieldNationality.setEditable(true);
        fieldAge.setEditable(true);
        fieldPhoto.setEditable(true);
        btnEdit.setVisible(false);
        btnEdit.setManaged(false);
        btnSave.setVisible(true);
        btnSave.setManaged(true);
        lblStatus.setText("");

    }

    @FXML
    private void onSaveClicked(){
        String nationality = fieldNationality.getText().trim();
        String ageText = fieldAge.getText().trim();
        String photo = fieldPhoto.getText().trim();

        int age;
        try{
            age = Integer.parseInt(ageText);
        }catch (NumberFormatException e){
            lblStatus.setText("Age must be a number.");
            return;
        }

        clientController.updateProfile(nationality, age, photo);

        fieldNationality.setEditable(false);
        fieldAge.setEditable(false);
        fieldPhoto.setEditable(false);
        btnSave.setVisible(false);
        btnSave.setManaged(false);
        btnEdit.setVisible(true);
        btnEdit.setManaged(true);
        lblStatus.setText("Profile saved.");

        clientController.readUserProfile();
    }

    @FXML
    private void onBackClicked(){
        viewManager.show(new MainMenuController());
    }



}
