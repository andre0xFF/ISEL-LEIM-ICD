package models;

import java.awt.*;

import org.junit.jupiter.api.Test;

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
        assertEquals(token, board.getToken(board.totalRows(), column));
    }

    @Test
    void shouldGetTokenInTheCorrectRow() {
        Board board = new Board();
        Token token = new Token(Color.BLUE);
        board.dropToken(1, token);

        assertEquals(token, board.getToken(board.totalRows(), 1));
    }
}
