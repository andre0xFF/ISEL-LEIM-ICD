package models;

import models.player.GamePlayView;
import models.player.Player;
import models.player.Token;
import models.player.Tokens;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ConnectFourTest {

    private Player player1;
    private Player player2;
    private ConnectFour connectFour;

    @BeforeEach
    void setUpEach() {
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

    @Test
    void shouldDrawOnGame1WhenNoPlayerWins() {
        connectFour.dropToken(1);
        connectFour.dropToken(3);
        connectFour.dropToken(2);
        connectFour.dropToken(4);
        connectFour.dropToken(5);
        connectFour.dropToken(7);
        connectFour.dropToken(6);
        connectFour.dropToken(1);
        connectFour.dropToken(3);
        connectFour.dropToken(2);
        connectFour.dropToken(4);
        connectFour.dropToken(5);
        connectFour.dropToken(7);
        connectFour.dropToken(6);
        connectFour.dropToken(1);
        connectFour.dropToken(3);
        connectFour.dropToken(2);
        connectFour.dropToken(4);
        connectFour.dropToken(5);
        connectFour.dropToken(7);
        connectFour.dropToken(6);
        connectFour.dropToken(1);
        connectFour.dropToken(3);
        connectFour.dropToken(2);
        connectFour.dropToken(4);
        connectFour.dropToken(5);
        connectFour.dropToken(7);
        connectFour.dropToken(6);
        connectFour.dropToken(1);
        connectFour.dropToken(3);
        connectFour.dropToken(2);
        connectFour.dropToken(4);
        connectFour.dropToken(5);
        connectFour.dropToken(7);
        connectFour.dropToken(6);
        connectFour.dropToken(1);
        connectFour.dropToken(3);
        connectFour.dropToken(2);
        connectFour.dropToken(4);
        connectFour.dropToken(5);
        connectFour.dropToken(7);
        connectFour.dropToken(6);

        assertTrue(this.connectFour.isGameOver());
        assertNull(this.connectFour.winner());
        assertTrue(this.connectFour.hasDraw());
    }

    @Test
    void shouldDrawOnGame2WhenNoPlayerWins() {
        connectFour.dropToken(1);
        connectFour.dropToken(2);
        connectFour.dropToken(1);
        connectFour.dropToken(2);
        connectFour.dropToken(1);
        connectFour.dropToken(2);
        connectFour.dropToken(2);
        connectFour.dropToken(1);
        connectFour.dropToken(2);
        connectFour.dropToken(1);
        connectFour.dropToken(2);
        connectFour.dropToken(1);

        connectFour.dropToken(3);
        connectFour.dropToken(4);
        connectFour.dropToken(3);
        connectFour.dropToken(4);
        connectFour.dropToken(4);
        connectFour.dropToken(3);
        connectFour.dropToken(4);
        connectFour.dropToken(3);
        connectFour.dropToken(3);
        connectFour.dropToken(4);
        connectFour.dropToken(3);
        connectFour.dropToken(4);

        connectFour.dropToken(5);
        connectFour.dropToken(6);
        connectFour.dropToken(5);
        connectFour.dropToken(6);
        connectFour.dropToken(6);
        connectFour.dropToken(5);
        connectFour.dropToken(6);
        connectFour.dropToken(5);
        connectFour.dropToken(5);
        connectFour.dropToken(6);
        connectFour.dropToken(5);
        connectFour.dropToken(6);

        connectFour.dropToken(7);
        connectFour.dropToken(7);
        connectFour.dropToken(7);
        connectFour.dropToken(7);
        connectFour.dropToken(7);
        connectFour.dropToken(7);

        assertTrue(this.connectFour.isGameOver());
        assertNull(this.connectFour.winner());
        assertTrue(this.connectFour.hasDraw());
    }
}
