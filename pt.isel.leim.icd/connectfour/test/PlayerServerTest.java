import models.player.GamePlayView;
import models.player.Token;
import models.player.Tokens;
import network.Client;
import network.Server;
import network.messages.DropTokenMessage;
import network.messages.LoginMessage;
import network.messages.Message;
import network.messages.PlayTurnMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerServerTest {

    private PlayerServer playerServer;
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

        this.playerServer = new PlayerServer(client);
        this.playerServer.gamePlayView(gamePlayView);

        assertTrue(playerServer.isConnected());

        this.client = server.accept();

        assertTrue(this.client.isConnected());
    }

    @Test
    void shouldAddToken() {
        playerServer.tokens(new Tokens());

        int actualTokens = playerServer.countTokens();
        int expectedTokens = actualTokens + 1;

        playerServer.addToken(new Token(playerServer.color()));

        assertEquals(expectedTokens, playerServer.countTokens());
    }

    @Test
    void shouldLoginWhenOnMessage() {
        playerServer.onMessage(new LoginMessage(
                "johndoe",
                new char[]{'a', 'b', 'c', '1', '2', '3'})
        );

        assertTrue(playerServer.isLogged());
        assertEquals("johndoe", playerServer.username());
    }

    @Test
    void shouldDropTokenWhenOnMessage() {
        this.playerServer.onMessage(new DropTokenMessage(1));

        assertTrue(tokenDropped);
    }

    @Test
    void shouldPlayTurnWhenOnMessage() throws IOException, SAXException {
        this.playerServer.playTurn();

        Message message = this.client.read();

        assertEquals(PlayTurnMessage.class, message.getClass());
    }
}
