import org.xml.sax.SAXException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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

            // Navigate from Start Menu to Log In Menu
            if(e.getSource().equals(this.view.startLogInButton())){
                this.view.connectFourLogIn();

            }

            // Navigate from SignUp Menu back to Starting Menu
            if(e.getSource().equals(this.view.signBackButton())){
                this.view.connectFourStartingMenu();
            }

            // Navigate from Start Menu to Sign Up Menu
            if(e.getSource().equals(this.view.startSignUpButton())){
                this.view.connectFourSignUp();
            }

            // Authentication Events
            if (e.getSource().equals(this.view.loginButton())) {
                this.model.login(
                        this.view.authUsernameField().getText(),
                        this.view.authPasswordField().getPassword()
                );
            }

            // Navigate from Log In Menu back to Starting Menu
            if(e.getSource().equals(this.view.logInBackButton())){
                this.view.connectFourStartingMenu();
            }

            // SignUp Events

            if(e.getSource().equals(this.view.signSubmitButton())){
                System.out.println("test");
                BufferedImage image = PhotoManager.downloadImage(this.view.signUpPictureField().getText());
                image = PhotoManager.resizeImage(image, 60, 60);

                this.model.signUp(
                        PhotoManager.encodeImage(image),
                        this.view.signUserNameField().getText(),
                        this.view.signPasswordField().getPassword(),
                        this.view.profEditNationalityField().getText(),
                        Integer.parseInt(this.view.signAgeField().getText())
                );

            }

            // Profile Events
            if (e.getSource().equals(this.view.profEditSubmitButton())) {
                this.model.updateProfile(
                        //TODO validate nationality input?
                        "image",
                        this.view.profEditUserNameField().getText(),
                        this.view.profEditPasswordField().getPassword(),
                        this.view.profEditNationalityField().getText(),
                        Integer.parseInt(this.view.profEditAgeField().getText())
                );
            }

            if(e.getSource().equals(this.view.profDispEditButton())){
                this.view.connectFourProfileEdit();

            }

            if(e.getSource().equals(this.view.profDispBackButton())){
                this.view.connectFourGameMenu();

            }

            // Cancel Queue Search
            if(e.getSource().equals(this.view.queueCancelButton())){
                this.view.connectFourGameMenu();
            }

            // Navigates from Profile Menu back to Game Menu
            if (e.getSource().equals(this.view.profEditBackButton())) {
                this.view.connectFourDisplayProfile();
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
                this.model.connectFourGamesStats();

            }

            // Navigate from Game Menu to Profile
            if (e.getSource().equals(this.view.profileButton())) {
                this.view.connectFourDisplayProfile();

            }


            // Game Menu quit button close app
            if (e.getSource().equals(this.view.quitButton())) {
                this.model.quitGame();
                this.view.connetFourCloseUI();

            }


            // Navigate from Game Menu to new Game Board
            if (e.getSource().equals(this.view.newGame())) {
                //TODO criar painel de espera da game queue
                this.view.connectFourQueueGame();
//                this.view.connectFourStartGame();
            }

            // Navigate from Game Over to Game Main Menu
            if(e.getSource().equals(this.view.gameOverExitButton())){
                this.view.connectFourGameMenu();
            }


            if(e.getSource().equals(this.view.gameBoardBackButton())){
                //TODO send message indicating surrender and update server game stats
                this.view.connectFourGameMenu();
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


    public void setGameOverScreen(String message){
        this.view.gameOverLabel().setText(message);
        this.view.connectFourGameOver();
    }

    public void dropToken(int column){
        this.view.boardTokenCells();
    }

    public void updateProfile(String image, String username, String password, String nationality, int age){

    }
}
