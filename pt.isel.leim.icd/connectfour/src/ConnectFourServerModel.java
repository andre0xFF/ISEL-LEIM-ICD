import messages.Message;
import messages.PingMessage;
import messages.PongMessage;
import models.ConnectFour;
import network.Client;
import network.Server;
import network.socket.Listener;

import java.io.IOException;

public class ConnectFourServerModel implements Listener<Message> {

    public static final int clientsLimit = 2;
    private ConnectFour connectFour;
    private final Server server = new Server();
    private final Client[] clients = new Client[ConnectFourServerModel.clientsLimit];

    public ConnectFourServerModel() throws IOException {
        for (int i = 0; i < ConnectFourServerModel.clientsLimit; i++) {
            Client client = this.server.accept();

            client.listen(this);
        }
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof PingMessage) {
            handle((PingMessage) message);
        }
        else if (message instanceof PongMessage) {
            handle((PongMessage) message);
        }
    }

    private void handle(PingMessage message) {
        // ...
    }

    private void handle(PongMessage message) {
        // ...
    }
}
