package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectFourTest {

    @Test
    void shouldDropToken() {
        ConnectFour connectFour = new ConnectFour(
                new Player("Player 1"),
                new Player("Player 2")
        );

        assertTrue(connectFour.dropToken(1));
    }

    @Test
    void shouldSwapPlayersWhenTokenDropped() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        ConnectFour connectFour = new ConnectFour(player1, player2);

        assertEquals(player1, connectFour.currentPlayer());

        connectFour.dropToken(1);

        assertEquals(player2, connectFour.currentPlayer());
    }

    @Test
    void shouldWinGameWhenFourTokensPlacedVertically() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

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
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

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
    void shouldWinGameWhenFourTokensPlacedDiagonally() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

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

        assertTrue(connectFour.isGameOver());
        assertEquals(player1, connectFour.winner());
    }
}
