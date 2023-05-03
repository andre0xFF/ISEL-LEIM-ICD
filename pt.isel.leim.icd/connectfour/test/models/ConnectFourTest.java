package models;

import models.player.GamePlayView;
import models.player.Player;
import models.player.Token;
import models.player.Tokens;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ConnectFourTest {

    private static Player player1;
    private static Player player2;
    private static ConnectFour connectFour;

    @BeforeAll
    static void setUp() {
        player1 = new Player() {

            private GamePlayView gamePlayView;
            private Tokens tokens = new Tokens(Color.RED);

            @Override
            public String username() {
                return "player1";
            }

            @Override
            public void addToken(Token token) {
                this.tokens.add(token);
            }

            @Override
            public Token popToken() {
                return this.tokens.pop();
            }

            @Override
            public int countTokens() {
                return this.tokens.size();
            }

            @Override
            public Color color() {
                return this.tokens.color();
            }

            @Override
            public void tokens(Tokens tokens) {
                this.tokens = tokens;
            }

            @Override
            public void playTurn() {

            }

            @Override
            public void gamePlayView(GamePlayView gamePlayView) {
                this.gamePlayView = gamePlayView;
            }
        };

        player2 = new Player() {

            private GamePlayView gamePlayView;
            private Tokens tokens = new Tokens(Color.BLUE);

            @Override
            public String username() {
                return "player2";
            }

            @Override
            public void addToken(Token token) {
                this.tokens.add(token);
            }

            @Override
            public Token popToken() {
                return this.tokens.pop();
            }

            @Override
            public int countTokens() {
                return this.tokens.size();
            }

            @Override
            public Color color() {
                return this.tokens.color();
            }

            @Override
            public void tokens(Tokens tokens) {
                this.tokens = tokens;
            }

            @Override
            public void playTurn() {

            }

            @Override
            public void gamePlayView(GamePlayView gamePlayView) {
                this.gamePlayView = gamePlayView;
            }
        };

        connectFour = new ConnectFour(player1, player2);
    }

    @Test
    void shouldDropToken() {
        assertTrue(connectFour.dropToken(1));
    }

    @Test
    void shouldSwapPlayersWhenTokenDropped() {
        Player currentPlayer = connectFour.currentPlayer();

        connectFour.dropToken(1);

        assertNotEquals(currentPlayer, connectFour.currentPlayer());
    }

    @Test
    void shouldWinGameWhenFourTokensPlacedVertically() {
        ConnectFour connectFour = new ConnectFour(player1, player2);

        connectFour.dropToken(1);
        connectFour.dropToken(2);
        connectFour.dropToken(1);
        connectFour.dropToken(3);
        connectFour.dropToken(1);
        connectFour.dropToken(4);
        connectFour.dropToken(1);

        assertTrue(connectFour.isGameOver());
        assertEquals(player1, connectFour.winner());
    }

    @Test
    void shouldWinGameWhenFourTokensPlacedHorizontally() {
        ConnectFour connectFour = new ConnectFour(player1, player2);

        connectFour.dropToken(1);
        connectFour.dropToken(1);
        connectFour.dropToken(2);
        connectFour.dropToken(1);
        connectFour.dropToken(3);
        connectFour.dropToken(1);
        connectFour.dropToken(4);

        assertTrue(connectFour.isGameOver());
        assertEquals(player1, connectFour.winner());
    }

    @Test
    void shouldWinGameWhenFourTokensPlacedDiagonallyFromBottomLeftToTopRight() {
        ConnectFour connectFour = new ConnectFour(player1, player2);

        // Player 1
        connectFour.dropToken(1);
        // Player 2
        connectFour.dropToken(2);
        // Player 1
        connectFour.dropToken(2);
        // Player 2
        connectFour.dropToken(3);
        // Player 1
        connectFour.dropToken(3);
        // Player 2
        connectFour.dropToken(4);
        // Player 1
        connectFour.dropToken(3);
        // Player 2
        connectFour.dropToken(4);
        // Player 1
        connectFour.dropToken(5);
        // Player 2
        connectFour.dropToken(4);
        // Player 1
        connectFour.dropToken(4);
    }

    @Test
    void shouldWinGameWhenFourTokensPlacedDiagonallyFromTopLeftToBottomRight() {
        ConnectFour connectFour = new ConnectFour(player1, player2);

        // Player 1
        connectFour.dropToken(4);
        // Player 2
        connectFour.dropToken(3);
        // Player 1
        connectFour.dropToken(3);
        // Player 2
        connectFour.dropToken(2);
        // Player 1
        connectFour.dropToken(1);
        // Player 2
        connectFour.dropToken(2);
        // Player 1
        connectFour.dropToken(2);
        // Player 2
        connectFour.dropToken(1);
        // Player 1
        connectFour.dropToken(5);
        // Player 2
        connectFour.dropToken(1);
        // Player 1
        connectFour.dropToken(1);

        assertTrue(connectFour.isGameOver());
        assertEquals(player1, connectFour.winner());
    }
}
