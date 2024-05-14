package pt.isel.icd;

import pt.isel.icd.communication.Client;
import pt.isel.icd.communication.SimpleSocketManager;
import pt.isel.icd.game.management.GameClientController;
import pt.isel.icd.serialization.XMLSerializer;
import pt.isel.icd.user.management.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientApplication {
    public static void main(String[] args) throws IOException, InterruptedException {
        SimpleSocketManager simpleSocketManager = new SimpleSocketManager();
        XMLSerializer xmlSerializer = new XMLSerializer();
        Client client = new Client(simpleSocketManager, xmlSerializer);
        UserClientRepository userClientRepository = new UserClientRepository();
        UserClientService userClientService = new UserClientService(userClientRepository);
        UserClientController userClientController = new UserClientController(simpleSocketManager, userClientService);
//        GameClientController gameClientController = new GameClientController(simpleSocketManager);

        JFrame frame = new JFrame("Othello");
        int BUTTON_SIZE = 60;
        int columns = 10;
        int rows = 10;
        frame.setSize(new Dimension(BUTTON_SIZE * columns, (BUTTON_SIZE * rows) + 40));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        StartClientView startClientView = new StartClientView(frame, userClientController);

        
        frame.setVisible(true);

//        client.addController(gameClientController);
//        client.addController(userClientController);
//        client.connect();
//
//        userClientController.authenticate(new User("user11", "password1234"));
//        Thread.sleep(250);
//        userClientController.readProfile();
    }
}
