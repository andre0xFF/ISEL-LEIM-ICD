package models;

public class Board {

    private final Token[][] tokens;
    private final int totalRows;
    private final int totalColumns;

    public Board() {
        tokens = new Token[6][7];
        totalRows = tokens.length;
        totalColumns = tokens[0].length;
    }

    public int dropToken(int column, Token token) {
        column -= 1;
        for (int row = totalRows - 1; row >= 0; row--) {
            if (tokens[row][column] == null) {
                tokens[row][column] = token;

                return row + 1;
            }
        }

        throw new RuntimeException("Column is full");
    }

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
