package pt.isel.icd.game.logic;

/**
 * Manages a Dots and Boxes game session.
 */
public class Game {

    public static final int DEFAULT_ROWS = 4; // 4x4 dots = 3x3 boxes
    public static final int DEFAULT_COLS = 4;

    private final Player[] players = new Player[2];
    private Board board;
    private Player currentPlayer;
    private Player winner;
    private Player loser;
    private GameState gameState = GameState.CLOSED;

    /**
     * Attempts to place a line on the board for the given player.
     * Implements the bonus rule: if a box is completed, the same player goes again.
     *
     * @param player the player making the move
     * @param dot1   one end of the line
     * @param dot2   the other end of the line
     * @return true if the line was placed successfully
     */
    public boolean placeLine(Player player, Dot dot1, Dot dot2) {
        if (!hasStarted()) {
            throw new IllegalStateException("Game has not started");
        }
        if (!isPlayerTurn(player)) {
            throw new IllegalStateException("It is not the player's turn");
        }

        Line line;
        try {
            line = new Line(dot1, dot2);
        } catch (IllegalArgumentException e) {
            return false;
        }

        if (!board.isLineValid(line)) {
            return false;
        }

        int boxesClosed = board.placeLine(line, player.marker());
        player.incrementScore(boxesClosed);

        if (board.isFull()) {
            calculateWinner();
            finish();
        } else if (boxesClosed == 0) {
            // No box completed: switch turns
            swapCurrentPlayer();
        }
        // If boxesClosed > 0, same player goes again (bonus rule)

        return true;
    }

    private void swapCurrentPlayer() {
        currentPlayer = currentPlayer.equals(players[0])
            ? players[1]
            : players[0];
    }

    private void calculateWinner() {
        if (players[0].score() > players[1].score()) {
            winner = players[0];
            loser = players[1];
        } else if (players[1].score() > players[0].score()) {
            winner = players[1];
            loser = players[0];
        } else if (players[0].score() == players[1].score()) {
            winner = null;
            loser = null;
        } else {
            throw new IllegalStateException("Unexpected score state");
        }
    }

    public Player winner() {
        return winner;
    }

    public Player loser() {
        return loser;
    }

    public Board board() {
        return board;
    }

    public Player currentPlayer() {
        return currentPlayer;
    }

    public Player getPlayer(int index) {
        return players[index];
    }

    // === Game lifecycle ===

    public void close() {
        gameState = GameState.CLOSED;
    }

    public boolean isClosed() {
        return gameState == GameState.CLOSED;
    }

    public void open() {
        gameState = GameState.OPEN;
    }

    public boolean isOpen() {
        return gameState == GameState.OPEN;
    }

    public void start() {
        if (!isOpen()) {
            throw new IllegalStateException("Game must be open to start");
        }
        if (players[0] == null || players[1] == null) {
            throw new IllegalStateException("Two players required to start");
        }

        board = new Board(DEFAULT_ROWS, DEFAULT_COLS);
        winner = null;
        loser = null;
        gameState = GameState.STARTED;
    }

    public boolean hasStarted() {
        return gameState == GameState.STARTED;
    }

    public void finish() {
        gameState = GameState.FINISHED;
    }

    public boolean isFinished() {
        return gameState == GameState.FINISHED;
    }

    public void join(Player player) {
        if (!isOpen()) {
            throw new IllegalStateException("Game is not open");
        }

        if (players[0] == null) {
            players[0] = player;
            currentPlayer = player;
        } else if (players[1] == null) {
            players[1] = player;
        } else {
            throw new IllegalStateException("Game is full");
        }
    }

    public void leave(Player player) {
        if (players[0] != null && players[0].equals(player)) {
            players[0] = null;
        } else if (players[1] != null && players[1].equals(player)) {
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

    public boolean isFull() {
        return players[0] != null && players[1] != null;
    }
}
