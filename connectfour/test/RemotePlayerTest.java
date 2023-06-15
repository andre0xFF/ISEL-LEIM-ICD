import models.Board;
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
    private int tokenDroppedColumn = -1;

    @BeforeEach
    void setUpEach() throws IOException {
        Server server = new Server(0);
        Client client = new Client(server.port());

        assertTrue(client.isConnected());

        GamePlayView gamePlayView = new GamePlayView() {
            private final Board board = new Board();

            @Override
            public boolean dropToken(int column) {
                tokenDroppedColumn = column;
                assertTrue(1 <= column && column <= this.board.totalColumns());

                return true;
            }
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
    void shouldLoginWhenMessageRead() throws IOException, SAXException {
        this.remotePlayer.onMessage(
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
    void shouldDropTokenWhenMessageRead() {
        this.remotePlayer.onMessage(new DropTokenMessage(1));

        assertEquals(1, this.tokenDroppedColumn);
    }

    @Test
    void shouldWriteOnTokenDroppedMessageWhenOnTokenDropped() throws IOException, SAXException {
        this.remotePlayer.onTokenDropped(1, 1, this.remotePlayer.color());

        Message message = this.client.read();

        assertInstanceOf(OnTokenDroppedMessage.class, message);

        OnTokenDroppedMessage onTokenDroppedMessage = (OnTokenDroppedMessage) message;

        assertEquals(1, onTokenDroppedMessage.column());
        assertEquals(1, onTokenDroppedMessage.row());
        assertEquals(this.remotePlayer.color(), onTokenDroppedMessage.color());
    }

    @Test
    void shouldWriteOnTokenNotDroppedMessageWhenOnTokenNotDropped() throws IOException, SAXException {
        this.remotePlayer.onTokenNotDropped(1);

        Message message = this.client.read();

        assertInstanceOf(OnTokenNotDroppedMessage.class, message);

        OnTokenNotDroppedMessage onTokenNotDroppedMessage = (OnTokenNotDroppedMessage) message;

        assertEquals(1, onTokenNotDroppedMessage.column());
    }

    @Test
    void shouldWriteOnPlayTurnMessageWhenOnPlayTurn() throws IOException, SAXException {
        this.remotePlayer.onPlayTurn();

        Message message = this.client.read();

        assertInstanceOf(OnPlayTurnMessage.class, message);
    }

    @Test
    void shouldWriteOnWaitTurnMessageWhenOnWaitTurn() throws IOException, SAXException {
        this.remotePlayer.onWaitTurn();

        Message message = this.client.read();

        assertInstanceOf(OnWaitTurnMessage.class, message);
    }

    @Test
    void shouldWriteOnLoseMessageWhenOnLose() throws IOException, SAXException {
        this.remotePlayer.onLose();

        Message message = this.client.read();

        assertInstanceOf(OnLoseMessage.class, message);
    }

    @Test
    void shouldWriteOnWinMessageWhenOnWin() throws IOException, SAXException {
        this.remotePlayer.onWin();

        Message message = this.client.read();

        assertInstanceOf(OnWinMessage.class, message);
    }

    @Test
    void shouldWriteOnDrawMessageWhenOnDraw() throws IOException, SAXException {
        this.remotePlayer.onDraw();

        Message message = this.client.read();

        assertInstanceOf(OnDrawMessage.class, message);
    }
}
