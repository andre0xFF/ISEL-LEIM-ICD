import models.player.GamePlayView;
import models.player.Token;
import models.player.Tokens;
import network.Client;
import network.Server;
import network.messages.DropTokenMessage;
import network.messages.LogInMessage;
import network.messages.Message;
import network.messages.PlayTurnMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RemotePlayerTest {

    private RemotePlayer remotePlayer;
    private Client client;
    private boolean tokenDropped = false;

    @BeforeEach
    void setUpEach() throws IOException {
        Server server = new Server(0);
        Client client = new Client(server.port());

        assertTrue(client.isConnected());

        GamePlayView gamePlayView = column -> {
            tokenDropped = true;
            return true;
        };

        this.remotePlayer = new RemotePlayer(client);
        this.remotePlayer.gamePlayView(gamePlayView);

        assertTrue(remotePlayer.isConnected());

        this.client = server.accept();

        assertTrue(this.client.isConnected());
    }

    @Test
    void shouldAddToken() {
        remotePlayer.tokens(new Tokens(remotePlayer.color()));

        int actualTokens = remotePlayer.countTokens();
        int expectedTokens = actualTokens + 1;

        remotePlayer.addToken(new Token(remotePlayer.color()));

        assertEquals(expectedTokens, remotePlayer.countTokens());
    }

    @Test
    void shouldLoginWhenOnMessage() {
        remotePlayer.onMessage(new LogInMessage(
                "johndoe",
                new char[]{'a', 'b', 'c', '1', '2', '3'})
        );

        assertTrue(remotePlayer.isLogged());
        assertEquals("johndoe", remotePlayer.username());
    }

    @Test
    void shouldDropTokenWhenOnMessage() {
        this.remotePlayer.onMessage(new DropTokenMessage(1));

        assertTrue(tokenDropped);
    }

    @Test
    void shouldPlayTurnWhenOnMessage() throws IOException, SAXException {
        this.remotePlayer.playTurn();

        Message message = this.client.read();

        assertEquals(PlayTurnMessage.class, message.getClass());
    }
}
