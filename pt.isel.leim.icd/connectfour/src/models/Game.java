package models;

import java.awt.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Game {

    private final Board board = new Board();
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private Player winner;
    private static final int totalToken = 21;

    public Game(Player player1, Player player2) {
        for (int i = 0; i < totalToken; i++) {
            player1.addToken(new Token(Color.ORANGE));
            player2.addToken(new Token(Color.BLUE));
        }

        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
    }

    public boolean dropToken(int column) {
        if (isGameOver()) {
            return false;
        }

        int row = board.dropToken(column, currentPlayer.getToken());

        if (checkWin(row, column)) {
            winner = currentPlayer;
            return true;
        }

        currentPlayer = currentPlayer == player1 ? player2 : player1;
        return true;
    }

    private boolean checkWin(int row, int column) {

        return checkHorizontalWin(row, column)
                || checkVerticalWin(row, column)
                || checkDiagonalWin(row, column);
    }

    private boolean checkDiagonalWin(int row, int column) {
        return false;
    }

    private boolean checkVerticalWin(int row, int column) {
        return false;
    }

    private boolean checkHorizontalWin(int row, int column) {

        for (int currentColumn = max(column - 3, 1), count = 0; currentColumn < min(column + 4, board.getTotalColumns()); currentColumn++) {
            if (board.getToken(row, currentColumn) == currentPlayer.getToken()) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        return false;
    }

    public boolean isGameOver() {
        return winner != null;
    }

    public Player getWinner() {
        return winner;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
