package pt.isel.icd.game.logic;

import pt.isel.icd.database.Entity;

public class Game implements Entity {
    public static final int BOARD_SIZE = 8;
    private final Player[] players = new Player[2];

    private Piece[][] board;
    private int totalPieces;
    private Player currentPlayer;
    private GameState gameState = GameState.CLOSED;

    public void initializeBoard() {
        board = new Piece[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                placePiece(i, j, Piece.EMPTY);
            }
        }

        placePiece(3, 3, players[0].playPiece());
        placePiece(3, 4, players[1].playPiece());
        placePiece(4, 3, players[0].playPiece());
        placePiece(4, 4, players[1].playPiece());
    }

    public boolean placePiece(Player player, int row, int column) {
        if (!isPlayerTurn(player)) {
            return false;
        }

        Piece piece = player.playPiece();

        if (!isMoveValid(row, column, piece)) {
            return false;
        }

        flipPieces(row, column, piece);
        placePiece(row, column, piece);

        currentPlayer = player == players[0] ? players[1] : players[0];

        return true;
    }

    private void placePiece(int row, int column, Piece player) {
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

    public boolean isGameOver() {
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
            placePiece(r, c, player);
            r += dr;
            c += dc;
        }
    }

    public Piece getPiece(int row, int column) {
        return board[row][column];
    }

    public void close() {
        gameState = GameState.CLOSED;
    }

    public boolean isClosed() {
        return gameState.equals(GameState.CLOSED);
    }

    public void open() {
        gameState = GameState.OPEN;
    }

    public boolean isOpen() {
        return gameState.equals(GameState.OPEN);
    }

    public void start() {
        initializeBoard();

        gameState = GameState.STARTED;
    }

    public boolean hasStarted() {
        return gameState.equals(GameState.STARTED);
    }

    public void finish() {
        gameState = GameState.FINISHED;
    }

    public boolean isFinished() {
        return gameState.equals(GameState.FINISHED);
    }

    public boolean join(Player player) {
        if (!isOpen()) {
            return false;
        }

        if (players[0] == null) {
            players[0] = player;
            currentPlayer = player;
        } else {
            players[1] = player;
        }

        return true;
    }

    public boolean leave(Player player) {
        if (!isOpen() || hasStarted()) {
            return false;
        }

        if (players[0] == player) {
            players[0] = null;

            return true;
        }

        if (players[1] == player) {
            players[1] = null;

            return true;
        }

        return false;
    }

    public boolean isPlayerTurn(Player player) {
        return hasStarted() && player.equals(currentPlayer);
    }

    public Piece[][] board() {
        return board;
    }

    public int countPlayers() {
        return (players[0] != null ? 1 : 0) + (players[1] != null ? 1 : 0);
    }
}
