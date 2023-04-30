import org.xml.sax.SAXException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ConnectFourPresenter implements ActionListener {
    private ConnectFourView view;
    private ConnectFourClientModel model;

    public ConnectFourPresenter(ConnectFourView view, ConnectFourClientModel model) {
        view.setActionListener(this);

        this.view = view;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

            // Authentication Events
            if (e.getSource().equals(this.view.loginButton())) {
                this.model.login(
                        this.view.authUsernameField().getText(),
                        this.view.authPasswordField().getPassword()
                );
            }

            // Profile Events
            if (e.getSource().equals(this.view.profileSubmitButton())) {
                this.model.updateProfile(
                        this.view.newNameField().getText(),
                        this.view.newPassword().getText().toCharArray(),
                        this.view.nationalityField().getText(),
                        Integer.parseInt(this.view.ageField().getText())
                );
            }

            // Navigates from Profile Menu back to Game Menu
            if (e.getSource().equals(this.view.profileBackButton())) {
                this.view.connectFourGameMenu();
            }

            // Game History Events

            // Navigates from Game History back to Game Menu
            if (e.getSource().equals(this.view.historyBackButton())) {
                this.view.connectFourGameMenu();

            }

            // Menu Events

            // Navigates from Game Menu to Game History
            if (e.getSource().equals(this.view.menuGameHistoryButton())) {
                this.view.connectFourGameHistoryPanel();
                this.model.connectFourGameHistory();

            }

            // Navigate from Game Menu to Profile
            if (e.getSource().equals(this.view.profileButton())) {
                this.view.connectFourProfile();

            }


            // Game Menu quit button close app
            if (e.getSource().equals(this.view.quitButton())) {
                this.model.quitGame();
                this.view.connetFourCloseUI();

            }


            // Navigate from Game Menu to new Game(Board)
            if (e.getSource().equals(this.view.newGame())) {
                //TODO criar painel de espera da game queue
//                this.view.connectFourQueueGame();
//                this.view.connectFourStartGame();
            }


            // GameBoardEvents
            JButton[][] boardTokenCells = this.view.boardTokenCells();
            for (int row = 0; row < boardTokenCells.length; row++) {
                for (int column = 0; column < boardTokenCells[0].length; column++) {
                    if (e.getSource().equals(boardTokenCells[row][column])) {
                        //TODO
                        this.model.dropToken(column);
                        break;
                    }
                }
            }


        } catch (IOException | SAXException ex) {

            throw new RuntimeException(ex);
        }


//        if (e.getSource().equals(view.newGameButton())) {
//
//        }

//        if (e.getSource().equals(view.dropTokenButton())) {
//            model.dropToken(view.column());
//        }
    }
}
