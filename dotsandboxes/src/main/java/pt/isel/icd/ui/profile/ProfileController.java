package pt.isel.icd.ui.profile;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import pt.isel.icd.ClientController;
import pt.isel.icd.GameEventListener;
import pt.isel.icd.ui.ViewController;
import pt.isel.icd.ui.ViewManager;
import pt.isel.icd.ui.menu.MainMenuController;
import pt.isel.icd.user.logic.Profile;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;


public class ProfileController implements ViewController, GameEventListener {

    private ViewManager viewManager;
    private ClientController clientController;

    @FXML private Label lblUsername;
    @FXML private TextField fieldNationality;
    @FXML private TextField fieldAge;
    @FXML private Label lblWins;
    @FXML private Label lblLosses;
    @FXML private Button btnEdit;
    @FXML private Button btnSave;
    @FXML private Label lblStatus;
    @FXML private ImageView imagePhoto;
    @FXML private Button btnChoosePhoto;

    private String photoBase64;




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
                lblWins.setText(String.valueOf(profile.wins()));
                lblLosses.setText(String.valueOf(profile.losses()));

                photoBase64 = profile.photo();
                if(photoBase64 != null && !photoBase64.isEmpty()){
                    try {
                        byte[] bytes = Base64.getDecoder().decode(photoBase64);
                        imagePhoto.setImage(new Image(new ByteArrayInputStream(bytes)));
                    }catch (IllegalArgumentException e){
                        lblStatus.setText("Failed to decode photo");
                    }
                }


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
        btnChoosePhoto.setVisible(true);
        btnChoosePhoto.setManaged(true);
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
        String photo = photoBase64.trim();

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

        btnChoosePhoto.setVisible(false);
        btnChoosePhoto.setManaged(false);

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

    @FXML
    private void onChoosePhotoClicked(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Photo");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                        "Image Files", "*.png", "*.jpg", "*.jpeg"));

        File file = fileChooser.showOpenDialog(imagePhoto.getScene().getWindow());
        if(file != null){
            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                photoBase64 = Base64.getEncoder().encodeToString(bytes);
                imagePhoto.setImage(new Image(new ByteArrayInputStream(bytes)));
            }catch (IOException e){
                lblStatus.setText("Failed to load image");
            }
        }
    }



}
