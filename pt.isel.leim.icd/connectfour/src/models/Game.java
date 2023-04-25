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

        int row = board.dropToken(column, currentPlayer.popToken());

        if (checkWin(row, column, currentPlayer)) {
            winner = currentPlayer;
            return true;
        }

        currentPlayer = currentPlayer == player1 ? player2 : player1;
        return true;
    }

    private boolean checkWin(int row, int column, Player player) {
        return checkHorizontalWin(row, column, player)
                || checkVerticalWin(row, column, player)
                || checkDiagonalWin(row, column, player);
    }

    private boolean checkDiagonalWin(int row, int column, Player player) {
        return checkDiagonalWin(row, column, player, 1, 1)
                || checkDiagonalWin(row, column, player, 1, -1)
                || checkDiagonalWin(row, column, player, -1, 1)
                || checkDiagonalWin(row, column, player, -1, -1);
    }

    private boolean checkDiagonalWin(int row, int column, Player player, int rowDirection, int columnDirection) {
        Color playerColor = player.color();

        for (
                int currentRow = row + (3 * rowDirection),
                        currentColumn = column - (3 * columnDirection),
                count = 0;
                currentRow > max(row - 4, 1)
                        && currentColumn > max(column - 4, 1)
                        && currentRow < min(row + 4, board.getTotalRows())
                        && currentColumn < min(column + 4, board.getTotalColumns());
                currentRow -= rowDirection,
                        currentColumn -= columnDirection
        ) {
            Token currentToken = board.getToken(currentRow, currentColumn);

            if (currentToken == null || !currentToken.color().equals(playerColor)) {
                count = 0;
                continue;
            }

            count++;

            if (count == 4) {
                return true;
            }
        }

        return false;
    }

    private boolean checkVerticalWin(int row, int column, Player player) {
        Color playerColor = player.color();

        for (
                int currentRow = min(row + 3, board.getTotalRows()),
                count = 0;
                currentRow > max(row - 4, 1);
                currentRow--
        ) {
            Token currentToken = board.getToken(currentRow, column);

            if (currentToken == null || !currentToken.color().equals(playerColor)) {
                count = 0;
                continue;
            }

            count++;

            if (count == 4) {
                return true;
            }
        }

        return false;
    }

    private boolean checkHorizontalWin(int row, int column, Player player) {
        Color playerColor = player.color();

        for (
                int currentColumn = max(column - 3, 1),
                count = 0;
                currentColumn < min(column + 4, board.getTotalColumns());
                currentColumn++
        ) {
            Token currentToken = board.getToken(row, currentColumn);

            if (currentToken == null || !currentToken.color().equals(playerColor)) {
                count = 0;
                continue;
            }

            count++;

            if (count == 4) {
                return true;
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
