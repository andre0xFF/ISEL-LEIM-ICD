import models.player.GamePlayView;
import models.player.Token;
import models.player.TokensStack;
import network.Client;
import network.Server;
import network.messages.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

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
        remotePlayer.tokens(new TokensStack(remotePlayer.color()));

        int actualTokens = remotePlayer.countTokens();
        int expectedTokens = actualTokens + 1;

        remotePlayer.addToken(new Token(remotePlayer.color()));

        assertEquals(expectedTokens, remotePlayer.countTokens());
    }

    @Test
    void shouldLoginWhenOnMessage() throws IOException, SAXException {
        remotePlayer.onMessage(
                new AskLoginMessage(
                        "johndoe",
                        new char[]{'a', 'b', 'c', '1', '2', '3'}
                )
        );

        assertTrue(remotePlayer.authenticated());
        assertEquals("johndoe", remotePlayer.username());

        Message message = this.client.read();

        assertInstanceOf(GiveLoginResultMessage.class, message);

        GiveLoginResultMessage giveLoginResultMessage = (GiveLoginResultMessage) message;

        assertTrue(giveLoginResultMessage.authenticated());
    }

    @Test
    void shouldDropTokenWhenOnMessage() {
        this.remotePlayer.onMessage(new DropTokenMessage(1));

        assertTrue(tokenDropped);
    }

    @Test
    void shouldPlayTurnWhenOnMessage() throws IOException, SAXException {
        this.remotePlayer.onPlayTurn();

        Message message = this.client.read();

        assertEquals(OnPlayTurnMessage.class, message.getClass());
    }
}
