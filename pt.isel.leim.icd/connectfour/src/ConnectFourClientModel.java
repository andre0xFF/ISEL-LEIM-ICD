import models.ConnectFour;
import network.Client;
import network.messages.*;
import org.xml.sax.SAXException;

import java.io.IOException;

public class ConnectFourClientModel {
    private ConnectFour connectFour;

    private final LocalPlayer player;

    private final Client client = new Client();

    public ConnectFourClientModel() throws IOException {
        this.player = new LocalPlayer(client);
    }

    public void dropToken(int column) throws IOException, SAXException {
        boolean dropped = connectFour.dropToken(column);

        if (dropped) {
            client.write(new DropTokenMessage(column));
        }
    }

    public void login(String text, char[] password) throws IOException, SAXException {
        client.write(new AskLogInMessage(text, password));
    }

    public void updateProfile(String image, String username, char[] password, String nationality, int age) throws IOException, SAXException {
        client.write(new AskUpdateProfileMessage(image, username, password, nationality, age));
    }

    public void signUp(String image, String username, char[] password, String nationality, int age) throws IOException, SAXException {
        client.write(new AskSignUpMessage(image, username, password, nationality, age));
    }



    public void connectFourGamesStats() throws IOException, SAXException {
        client.write(new AskGamesStatsMessage());
    }

    public void quitGame() throws IOException {
//        client.write(new GameOverMessage());
//        client.close();
    }
}
