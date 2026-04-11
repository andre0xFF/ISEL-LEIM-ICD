package pt.isel.icd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isel.icd.communication.Client;
import pt.isel.icd.communication.SchemaValidator;
import pt.isel.icd.communication.SimpleSocketManager;
import pt.isel.icd.serialization.CommandRegistry;
import pt.isel.icd.serialization.CommandSerializer;
import pt.isel.icd.ui.ViewManager;
import pt.isel.icd.ui.auth.LoginController;

public class ClientApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Serialization
        CommandSerializer commandSerializer = new CommandSerializer();
        CommandRegistry.registerAll(commandSerializer);

        // Validation
        SchemaValidator schemaValidator = new SchemaValidator();

        // Communication
        SimpleSocketManager simpleSocketManager = new SimpleSocketManager();
        Client client = new Client(
            simpleSocketManager,
            commandSerializer,
            schemaValidator
        );

        // Controller
        ClientController clientController = new ClientController(
            simpleSocketManager
        );

        // Authentication
        simpleSocketManager.setAuthenticator(clientController);

        // Register controller
        client.addController(clientController);

        // Load FXML and wire up
        ViewManager viewManager = new ViewManager(stage, clientController);
        client.connect();
        stage.setTitle("Dots and Boxes");
        viewManager.show(new LoginController());
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
