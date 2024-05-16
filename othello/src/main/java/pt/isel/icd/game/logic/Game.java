package pt.isel.icd.game.logic;

import pt.isel.icd.patterns.verticals.Entity;

public class Game implements Entity {
    public static final int BOARD_SIZE = 8;
    private Piece[][] board;
    private int totalPieces;

    public Game() {
        initializeBoard();
    }

    private void initializeBoard() {
        board = new Piece[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                setPiece(i, j, Piece.EMPTY);
            }
        }

        setPiece(3, 3, Piece.X);
        setPiece(3, 4, Piece.O);
        setPiece(4, 3, Piece.O);
        setPiece(4, 4, Piece.X);
    }

    public boolean makeMove(int row, int column, Piece player) {
        if (!isMoveValid(row, column, player)) {
            return false;
        }

        flipPieces(row, column, player);
        setPiece(row, column, player);

        return true;
    }

    private void setPiece(int row, int column, Piece player) {
        if (player != Piece.EMPTY) {
            totalPieces++;
        }

        board[row][column] = player;
    }

    /**
     * Validates if the move is valid
     *
     * @param row             the row
     * @param column          the column
     * @param playerCharacter the playerCharacter character
     * @return true if the move is valid, false otherwise
     */
    private boolean isMoveValid(int row, int column, Piece playerCharacter) {
//        if (checkEndGame()) {
//            return false;
//        }

        if (board[row][column] != Piece.EMPTY) {
            return false;
        }

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                // Skip the current position
                if (dr == 0 && dc == 0) {
                    continue;
                }

                if (checkDirection(row, column, dr, dc, playerCharacter)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean checkEndGame() {
//        if (totalPieces != BOARD_SIZE * BOARD_SIZE) {
//            return false;
//        }

        if (totalPieces == BOARD_SIZE * BOARD_SIZE) {
            return true;
        }

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (isMoveValid(i, j, Piece.X) || isMoveValid(i, j, Piece.O)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkDirection(int row, int column, int dr, int dc, Piece playerCharacter) {
        int r = row + dr;
        int c = column + dc;
        boolean validDir = false;


        while (r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE) {
            if (board[r][c] == Piece.EMPTY) {
                return false;
            }

            if (board[r][c] != playerCharacter) {
                validDir = true;
            }

            if (board[r][c] == playerCharacter && validDir) {
                return true;
            }

            r += dr;
            c += dc;
        }

        return false;
    }

    private void flipPieces(int x, int y, Piece player) {
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) {
                    continue;
                }

                if (checkDirection(x, y, dr, dc, player)) {
                    flipDirection(x, y, dr, dc, player);
                }
            }
        }
    }

    private void flipDirection(int x, int y, int dr, int dc, Piece player) {
        int r = x + dr;
        int c = y + dc;

        Piece opponent = player == Piece.X ? Piece.O : Piece.X;

        while (board[r][c] == opponent) {
            setPiece(r, c, player);
            r += dr;
            c += dc;
        }
    }

    public Piece getPiece(int row, int column) {
        return board[row][column];
    }
}