package pt.isel.icd.game.logic;

import pt.isel.icd.database.Entity;

public class Game implements Entity {
    public static final int BOARD_SIZE = 8;
    private final Player[] players = new Player[2];

    private Piece[][] board;
    private int totalPieces;
    private Player currentPlayer;
    private Player winner;
    private Player loser;
    private GameState gameState = GameState.CLOSED;

    public void initializeBoard() {
        if (!isOpen() || !hasStarted()) {
            throw new IllegalStateException("Game is not open or has not started");
        }

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
        if (!hasStarted()) {
            throw new IllegalStateException("Game has not started");
        }

        if (!isPlayerTurn(player)) {
            throw new IllegalStateException("It is not the player's turn");
        }

        Piece piece = player.playPiece();

        if (!isMoveValid(row, column, piece)) {
            return false;
        }

        flipPieces(row, column, piece);
        placePiece(row, column, piece);

        if (!checkGameOver()) {
            swapCurrentPlayer();
        } else {
            calculateScores();
            calculateWinner();
            finish();
        }

        return true;
    }

    private void swapCurrentPlayer() {
        currentPlayer = currentPlayer.equals(players[0]) ? players[1] : players[0];
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
        if (board[row][column] != Piece.EMPTY) {
            return false;
        }

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                // Skip the current position
                if (dr == 0 && dc == 0) {
                    continue;
                }

                if (isMoveValidDirection(row, column, dr, dc, playerCharacter)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isMoveValidDirection(int row, int column, int dr, int dc, Piece piece) {
        int r = row + dr;
        int c = column + dc;
        boolean validDirection = false;

        while (r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE) {
            if (board[r][c] == Piece.EMPTY) {
                return false;
            }

            if (board[r][c] != piece) {
                validDirection = true;
            }

            if (board[r][c] == piece && validDirection) {
                return true;
            }

            r += dr;
            c += dc;
        }

        return false;
    }

    private void calculateScores() {
        players[0].resetScore();
        players[1].resetScore();

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == players[0].playPiece()) {
                    players[0].incrementScore();
                }
                else if (board[i][j] == players[1].playPiece()) {
                    players[1].incrementScore();
                }
            }
        }
    }

    private void calculateWinner() {
        if (players[0].score() > players[1].score()) {
            winner = players[0];
            loser = players[1];
        }
        else if (players[0].score() < players[1].score()) {
            winner = players[1];
            loser = players[0];
        }
    }

    public Player winner() {
        return winner;
    }

    public Player loser() {
        return loser;
    }

    private boolean checkGameOver() {
        return checkBoardFull() || checkNoMoves();
    }

    private boolean checkNoMoves() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (isMoveValid(i, j, players[0].playPiece()) || isMoveValid(i, j, players[1].playPiece())) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkBoardFull() {
        return totalPieces != BOARD_SIZE * BOARD_SIZE;
    }

    private void flipPieces(int x, int y, Piece player) {
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) {
                    continue;
                }

                if (isMoveValidDirection(x, y, dr, dc, player)) {
                    flipPiecesDirection(x, y, dr, dc, player);
                }
            }
        }
    }

    private void flipPiecesDirection(int x, int y, int dr, int dc, Piece player) {
        int r = x + dr;
        int c = y + dc;

        Piece opponent = player == Piece.X ? Piece.O : Piece.X;

        while (board[r][c] == opponent) {
            placePiece(r, c, player);

            r += dr;
            c += dc;
        }
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

        winner = null;
        loser = null;
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

    public void join(Player player) {
        if (!isOpen() || hasStarted()) {
            throw new IllegalStateException("Game is not open or has already started");
        }

        if (players[0] == null) {
            players[0] = player;
            currentPlayer = player;
        }
        else if (players[1] == null) {
            players[1] = player;
        }
    }

    public void leave(Player player) {
        if (!hasStarted()) {
            throw new IllegalStateException("Game has not started");
        }

        if (players[0].equals(player)) {
            players[0] = null;
        }
        else if (players[1].equals(player)) {
            players[1] = null;
        }

        gameState = GameState.OPEN;
    }

    public boolean isPlayerTurn(Player player) {
        return hasStarted() && player.equals(currentPlayer);
    }

    public boolean hasWinner() {
        return winner != null;
    }
}
