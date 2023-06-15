import models.ConnectFour;
import network.Client;
import network.messages.*;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * The client model of the Connect Four game. It is responsible for managing the game, the player and the opponent
 * player.
 * An instance of this class connects to the server automatically.
 */
public class ConnectFourClientModel {

    private final Client client = new Client();
    private final LocalPlayer player = new LocalPlayer(this.client);
    private final LocalOpponentPlayer opponentPlayer = new LocalOpponentPlayer();
    private ConnectFour connectFour = new ConnectFour(this.player, this.opponentPlayer);

    public ConnectFourClientModel() throws IOException {
    }

    public void dropToken(int column) throws IOException, SAXException {
        boolean dropped = connectFour.dropToken(column);

        if (dropped) {
            this.client.write(new DropTokenMessage(column));
        }
    }

    public void login(String text, char[] password) throws IOException, SAXException {
        this.client.write(new AskLoginMessage(text, password));
    }

    public void updateProfile(String image, String username, char[] password, String nationality, int age) throws IOException, SAXException {
        this.client.write(new AskUpdateProfileMessage(image, username, password, nationality, age));
    }

    public void signUp(String image, String username, char[] password, String nationality, int age) throws IOException, SAXException {
        this.client.write(new AskSignUpMessage(image, username, password, nationality, age));
    }


    public void connectFourGamesStats() throws IOException, SAXException {
        this.client.write(new AskGamesStatsMessage());
    }

    public void quitGame() throws IOException {
//        client.write(new GameOverMessage());
//        client.close();
    }
}
