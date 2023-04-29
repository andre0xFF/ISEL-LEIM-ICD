package models;

import java.awt.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class ConnectFour implements Model {

    private final Board board = new Board();
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private Player winner;
    private static final int totalToken = 21;

    public ConnectFour(Player player1, Player player2) {
        for (int i = 0; i < totalToken; i++) {
            player1.addToken(new Token(Color.ORANGE));
            player2.addToken(new Token(Color.BLUE));
        }

        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
    }

    /**
     * Drops a token in the specified column
     *
     * @param column the column to drop the token in
     * @return true if the token was dropped, false if the game is over or the column is full
     */
    public boolean dropToken(int column) {
        if (isGameOver()) {
            return false;
        }

        int row;

        try {
            row = board.dropToken(column, currentPlayer.popToken());
        } catch (RuntimeException e) {
            return false;
        }

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
        Color playerColor = player.color();

        for (int currentRow = min(row + 3, board.totalRows()),
             count = 0;
             currentRow > max(row - 4, 1);
             currentRow--
        ) {
            for (int currentColumn = max(column - 3, 1);
                 currentColumn < min(column + 4, board.totalColumns());
                 currentColumn++
            ) {
                Token currentToken = board.getToken(row, currentColumn);
                if (currentToken == null || !currentToken.color().equals(playerColor)) {
                    count = 0;
                    continue;
                }
                count++;
                break;

            }
            if (count == 4) {
                return true;
            }
        }

        return false;
    }

    private boolean checkVerticalWin(int row, int column, Player player) {
        Color playerColor = player.color();

        for (
                int currentRow = min(row + 3, board.totalRows()),
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
                currentColumn < min(column + 4, board.totalColumns());
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

    /**
     * Checks if the game is over
     *
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return winner != null;
    }

    /**
     * Gets the winner of the game
     *
     * @return the winner of the game, or null if the game is not over
     */
    public Player winner() {
        return winner;
    }

    /**
     * Gets the current player
     *
     * @return the current player
     */
    public Player currentPlayer() {
        return currentPlayer;
    }
}
