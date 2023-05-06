package models;

import models.player.GamePlayView;
import models.player.Player;
import models.player.Token;

import java.awt.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * A Connect Four game
 */
public class ConnectFour implements GamePlayView {

    private final Board board = new Board();
    private Player currentPlayer;
    private Player otherPlayer;
    private Player winner;

    /**
     * Creates a new Connect Four game
     *
     * @param player1 the first player
     * @param player2 the second player
     */
    public ConnectFour(Player player1, Player player2) {
        player1.gamePlayView(this);
        player2.gamePlayView(this);

        this.currentPlayer = player1;
        this.otherPlayer = player2;

        this.currentPlayer.onPlayTurn();
    }

    /**
     * Drops a token in the specified column
     *
     * @param column the column to drop the token in
     * @return true if the token was dropped, false if the game is over or the column is full
     */
    @Override
    public boolean dropToken(int column) {
        if (isGameOver()) {
            this.currentPlayer.onTokenNotDropped(column);
            return false;
        }

        int row;

        try {
            row = this.board.dropToken(column, this.currentPlayer.popToken());
        } catch (RuntimeException e) {
            this.currentPlayer.onTokenNotDropped(column);
            return false;
        }

        this.currentPlayer.onTokenDropped(column, row, this.board.token(row, column).color());
        this.otherPlayer.onTokenDropped(column, row, this.board.token(row, column).color());

        if (checkWin(row, column, this.currentPlayer)) {
            setWinner();
            return true;
        }

        swapCurrentPlayer();

        return true;
    }

    private void swapCurrentPlayer() {
        this.currentPlayer.onWaitTurn();

        Player temporaryPlayer = this.currentPlayer;
        this.currentPlayer = this.otherPlayer;
        this.otherPlayer = temporaryPlayer;

        this.currentPlayer.onPlayTurn();
    }

    private void setWinner() {
        this.winner = this.currentPlayer;

        this.currentPlayer.onWin();
        this.otherPlayer.onLoss();
    }

    private boolean checkWin(int row, int column, Player player) {
        return checkHorizontalWin(row, column, player)
                || checkVerticalWin(row, column, player)
                || checkDiagonalWin(row, column, player);
    }

    private boolean checkDiagonalWin(int row, int column, Player player) {
        return checkDiagonalWin(row, column, player, 1, 1, 0)
                || checkDiagonalWin(row, column, player, 1, -1, 0)
                || checkDiagonalWin(row, column, player, -1, 1, 0)
                || checkDiagonalWin(row, column, player, -1, -1, 0);
    }

    private boolean checkDiagonalWin(int row, int column, Player player, int rowDirection, int columnDirection, int count) {
        Color playerColor = player.color();

        if (row < 1 || row > board.totalRows() || column < 1 || column > board.totalColumns()) {
            return false;
        }

        Token currentToken = board.token(row, column);

        if (currentToken == null || !currentToken.color().equals(playerColor)) {
            return false;
        }
        count++;

        if (count == 4) {
            return true;
        }

        return checkDiagonalWin(row + rowDirection, column + columnDirection, player, rowDirection, columnDirection, count);
    }

    private boolean checkVerticalWin(int row, int column, Player player) {
        Color playerColor = player.color();

        for (
                int currentRow = min(row + 3, board.totalRows()),
                count = 0;
                currentRow > max(row - 4, 1);
                currentRow--
        ) {
            Token currentToken = board.token(currentRow, column);

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
            Token currentToken = board.token(row, currentColumn);

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
        return hasWinner() || hasDraw();
    }

    /**
     * Checks if the game is a draw
     *
     * @return true if the game is a draw, false otherwise
     */
    public boolean hasDraw() {
        return this.board.isFull();
    }

    public boolean hasWinner() {
        return this.winner != null;
    }

    /**
     * Gets the winner of the game
     *
     * @return the winner of the game, or null if the game is not over
     */
    public Player winner() {
        return this.winner;
    }

    /**
     * Gets the current player
     *
     * @return the current player
     */
    public Player currentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns the total number of rows in the board.
     *
     * @return The total number of rows in the board.
     */
    public int totalRows() {
        return this.board.totalRows();
    }

    /**
     * Returns the total number of columns in the board.
     *
     * @return The total number of columns in the board.
     */
    public int totalColumns() {
        return this.board.totalColumns();
    }
}
