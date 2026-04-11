package pt.isel.icd.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isel.icd.ClientController;
import java.io.IOException;



/**
 *  It holds the Stage and swaps scenes by loading the FXML,
 *  wiring up the controller, and calling stage.setScene(...).
 * **/
public class ViewManager {

    private final Stage stage;
    private final ClientController clientController;

    public ViewManager(Stage stage, ClientController clientController){
        this.stage = stage;
        this.clientController = clientController;
    }


    public void show(ViewController controller) {
        //TODO strange needs checking
        try{



        FXMLLoader loader = new FXMLLoader(getClass().getResource(controller.getFxmlPath()));
        Parent root = loader.load();
        ViewController fxmlController = loader.getController();
        fxmlController.setClientController(clientController);
        fxmlController.setViewManager(this);
        stage.setScene(new Scene(root));

        }catch (IOException e){
            throw new RuntimeException("Failed to load view for " + controller.getClass().getSimpleName(), e);
        }

    }

}
