import models.player.GamePlayView;
import network.Client;
import network.Server;
import network.messages.GiveLogInAcceptedMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LocalPlayerTest {

    private LocalPlayer localPlayer;
    private ConnectFourPresenter presenter;
    private boolean tokenDropped = false;


    private Client client;


    @BeforeEach
    void setUpEach() throws IOException {
        Server server = new Server(0);
        Client client = new Client(server.port());

        assertTrue(client.isConnected());


        GamePlayView gamePlayView = column -> {
            tokenDropped = true;
            return true;
        };

        ConnectFourView view = new ConnectFourView(6,6);
        ConnectFourClientModel model = new ConnectFourClientModel();
        this.presenter = new ConnectFourPresenter(view, model);
        this.localPlayer = new LocalPlayer(client);
        this.localPlayer.gamePlayView(gamePlayView);

        assertTrue(localPlayer.isConnected());

    }

    @Test
    void shouldLogInWhenGiveOnLogInAcceptedMessage(){
        this.localPlayer.onMessage(new GiveLogInAcceptedMessage());

    }


}
