package pt.isel.icd;

import com.sun.tools.javac.Main;
import pt.isel.icd.communication.Client;
import pt.isel.icd.communication.SimpleSocketManager;
import pt.isel.icd.game.management.GameClientController;
import pt.isel.icd.serialization.XMLSerializer;
import pt.isel.icd.user.logic.User;
import pt.isel.icd.user.management.*;

import java.io.IOException;

public class ClientApplication {
    public static void main(String[] args) throws IOException {
        SimpleSocketManager simpleSocketManager = new SimpleSocketManager();
        XMLSerializer xmlSerializer = new XMLSerializer();
        Client client = new Client(simpleSocketManager, xmlSerializer);
        UserClientController userClientController = new UserClientController(simpleSocketManager);
        GameClientController gameClientController = new GameClientController(simpleSocketManager);
        // UserFrame userFrame = new UserFrame();
        // StartClientView startClientView = new StartClientView(userFrame, userClientController);

        simpleSocketManager.addMiddleware(new AuthenticationSimpleSocketMiddleware(userClientController));

//        client.addController(gameClientController);
//        client.addController(userClientController);
//        client.connect();

//        JFrame frame = new JFrame("Othello");
//        int BUTTON_SIZE = 60;
//        int columns = 10;
//        int rows = 10;
//        frame.setSize(new Dimension(BUTTON_SIZE * columns, (BUTTON_SIZE * rows) + 40));
//        frame.setVisible(true);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        StartClientView startClientView = new StartClientView(frame, userClientController);
//
//        UserClientView userClientView = new UserClientView(frame, userClientController);


        gameClientController.joinGame();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        gameClientController.placePiece(1, 1);
    }
}
