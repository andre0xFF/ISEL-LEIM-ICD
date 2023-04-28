import messages.LoginMessage;
import models.ConnectFour;
import org.xml.sax.SAXException;

import java.io.IOException;

public class ConnectFourClientModel {
    private ConnectFour connectFour;
    private final Client client = new Client();

    public ConnectFourClientModel() throws IOException {
    }

    public void dropToken(int column) {
//        boolean dropped = connectFour.dropToken(column);
//
//        if (dropped) {
//            client.write(new DropTokenMessage(column));
//        }
    }

    public void login(String text, char[] password) throws IOException, SAXException {
        client.write(new LoginMessage(text, password));
    }
}
