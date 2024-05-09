package pt.isel.icd.game.logic;

import pt.isel.icd.patterns.verticals.Entity;

public class GameEntity implements Entity {
    public static final int BOARD_SIZE = 8;
    private PieceEntity[][] board;
    private int totalPieces;

    public GameEntity() {
        initializeBoard();
    }

    private void initializeBoard() {
        board = new PieceEntity[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                setPiece(i, j, PieceEntity.EMPTY);
            }
        }

        setPiece(3, 3, PieceEntity.X);
        setPiece(3, 4, PieceEntity.O);
        setPiece(4, 3, PieceEntity.O);
        setPiece(4, 4, PieceEntity.X);
    }

    public boolean makeMove(int row, int column, PieceEntity player) {
        if (!isMoveValid(row, column, player)) {
            return false;
        }

        flipPieces(row, column, player);
        setPiece(row, column, player);

        return true;
    }

    private void setPiece(int row, int column, PieceEntity player) {
        if (player != PieceEntity.EMPTY) {
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
    private boolean isMoveValid(int row, int column, PieceEntity playerCharacter) {
//        if (checkEndGame()) {
//            return false;
//        }

        if (board[row][column] != PieceEntity.EMPTY) {
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
                if (isMoveValid(i, j, PieceEntity.X) || isMoveValid(i, j, PieceEntity.O)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkDirection(int row, int column, int dr, int dc, PieceEntity playerCharacter) {
        int r = row + dr;
        int c = column + dc;
        boolean validDir = false;


        while (r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE) {
            if (board[r][c] == PieceEntity.EMPTY) {
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

    private void flipPieces(int x, int y, PieceEntity player) {
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

    private void flipDirection(int x, int y, int dr, int dc, PieceEntity player) {
        int r = x + dr;
        int c = y + dc;

        PieceEntity opponent = player == PieceEntity.X ? PieceEntity.O : PieceEntity.X;

        while (board[r][c] == opponent) {
            setPiece(r, c, player);
            r += dr;
            c += dc;
        }
    }

    public PieceEntity getPiece(int row, int column) {
        return board[row][column];
    }
}