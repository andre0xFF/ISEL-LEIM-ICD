package models;

import models.player.GamePlayView;
import models.player.Player;
import models.player.Token;
import models.player.Tokens;

import java.awt.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class ConnectFour implements GamePlayView {

    private final Board board = new Board();
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private Player winner;

    public ConnectFour(Player player1, Player player2) {
        player1.gamePlayView(this);
        player2.gamePlayView(this);

        for (int i = 0; i < Tokens.MAX_TOKENS; i++) {
            player1.addToken(new Token(Color.ORANGE));
            player2.addToken(new Token(Color.BLUE));
        }

        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;

        this.currentPlayer.playTurn();
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
            return false;
        }

        int row;

        try {
            row = this.board.dropToken(column, this.currentPlayer.popToken());
        } catch (RuntimeException e) {
            return false;
        }

        if (checkWin(row, column, currentPlayer)) {
            this.winner = this.currentPlayer;
            return true;
        }

        this.currentPlayer = this.currentPlayer == this.player1 ? this.player2 : this.player1;

        return true;
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

        Token currentToken = board.getToken(row, column);

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
}
