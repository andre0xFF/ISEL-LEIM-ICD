package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void shouldDropToken() {
        Game game = new Game(
                new Player("Player 1"),
                new Player("Player 2")
        );

        assertTrue(game.dropToken(1));
    }

    @Test
    void shouldSwapPlayersWhenTokenDropped() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        Game game = new Game(player1, player2);

        assertEquals(player1, game.getCurrentPlayer());

        game.dropToken(1);

        assertEquals(player2, game.getCurrentPlayer());
    }

    @Test
    void shouldWinGameVertically() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        Game game = new Game(player1, player2);

        game.dropToken(1);
        game.dropToken(2);
        game.dropToken(1);
        game.dropToken(3);
        game.dropToken(1);
        game.dropToken(4);
        game.dropToken(1);

        assertTrue(game.isGameOver());
        assertEquals(player1, game.getWinner());
    }

    @Test
    void shouldWinGameHorizontally() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        Game game = new Game(player1, player2);

        game.dropToken(1);
        game.dropToken(1);
        game.dropToken(2);
        game.dropToken(1);
        game.dropToken(3);
        game.dropToken(1);
        game.dropToken(4);

        assertTrue(game.isGameOver());
        assertEquals(player1, game.getWinner());
    }

    @Test
    void shouldWinGameDiagonally() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        Game game = new Game(player1, player2);

        // Player 1
        game.dropToken(1);
        // Player 2
        game.dropToken(2);
        // Player 1
        game.dropToken(2);
        // Player 2
        game.dropToken(3);
        // Player 1
        game.dropToken(3);
        // Player 2
        game.dropToken(4);
        // Player 1
        game.dropToken(3);
        // Player 2
        game.dropToken(4);
        // Player 1
        game.dropToken(5);
        // Player 2
        game.dropToken(4);
        // Player 1
        game.dropToken(4);

        assertTrue(game.isGameOver());
        assertEquals(player1, game.getWinner());
    }
}
