package models;

/**
 * Represents the Connect Four board in the game.
 */
public class Board {

    private final Token[][] tokens = new Token[6][7];
    private final int totalRows = tokens.length;
    private final int totalColumns = tokens[0].length;

    /**
     * Drops a token into the specified column if the column is not full. If the column is full, a RuntimeException is
     * thrown.
     * @param column The column to drop the token into in a range of 1 to 7.
     * @param token The token to drop into the column.
     * @return The row the token was dropped into.
     */
    public int dropToken(int column, Token token) throws RuntimeException {
        column -= 1;
        for (int row = totalRows - 1; row >= 0; row--) {
            if (tokens[row][column] == null) {
                tokens[row][column] = token;

                return row + 1;
            }
        }

        throw new RuntimeException("Column is full");
    }

    /**
     * Returns the token at the specified row and column.
     * @param row The row of the token in a range of 1 to 6.
     * @param column The column of the token in a range of 1 to 7.
     * @return The token at the specified row and column.
     */
    public Token getToken(int row, int column) {
        row -= 1;
        column -= 1;

        return tokens[row][column];
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }
}
