import network.messages.*;
import models.ConnectFour;
import network.Client;
import org.xml.sax.SAXException;

import java.io.IOException;

public class ConnectFourClientModel {
    private ConnectFour connectFour;
    private final Client client = new Client();

    public ConnectFourClientModel() throws IOException {
    }

    public void dropToken(int column) throws IOException, SAXException {
        boolean dropped = connectFour.dropToken(column);

        if (dropped) {
            client.write(new DropTokenMessage(column));
        }
    }

    public void login(String text, char[] password) throws IOException, SAXException {
        client.write(new LoginMessage(text, password));
    }

    public void updateProfile(String username, char[] password, String nationality, int age) throws IOException, SAXException {
        client.write(new UpdateProfileMessage(username, password, nationality, age));
    }

    public void signUp(String image, String username, char[] password, String nationality, int age) throws IOException, SAXException {
        client.write(new SignUpMessage(image, username, password, nationality, age));
    }



    public void connectFourGameHistory() throws IOException, SAXException {
        client.write(new AskGameHistoryMessage());
    }

    public void quitGame() throws IOException {
//        client.write(new GameOverMessage());
//        client.close();
    }
}
