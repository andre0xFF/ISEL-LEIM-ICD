package models;

import models.player.Token;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void shouldDropToken() {
        Board board = new Board();
        Token token = new Token(Color.BLUE);
        int column = 1;

        assertDoesNotThrow(() -> board.dropToken(column, token));
    }

    @Test
    void shouldNotDropTokenWhenColumnIsFull() {
        Board board = new Board();
        Token token = new Token(Color.BLUE);
        int column = 1;

        for (int i = 0; i < board.totalRows(); i++) {
            board.dropToken(column, token);
        }

        assertThrows(RuntimeException.class, () -> board.dropToken(column, token));
    }

    @Test
    void shouldStoreTokenWhenTokenDropped() {
        Board board = new Board();
        Token token = new Token(Color.BLUE);
        int column = 1;

        board.dropToken(column, token);
        assertEquals(token, board.token(board.totalRows(), column));
    }

    @Test
    void shouldGetTokenInTheCorrectRow() {
        Board board = new Board();
        Token token = new Token(Color.BLUE);
        int column = 1;

        board.dropToken(column, token);

        assertEquals(token, board.token(board.totalRows(), column));
    }
}
