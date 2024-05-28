package pt.isel.icd.game.management;


import com.fasterxml.jackson.core.JsonProcessingException;
import pt.isel.icd.communication.ConnectedCommand;
import pt.isel.icd.communication.ConnectionManager;
import pt.isel.icd.communication.DisconnectedCommand;
import pt.isel.icd.game.logic.Game;
import pt.isel.icd.game.logic.Piece;
import pt.isel.icd.game.logic.Player;
import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;
import pt.isel.icd.patterns.verticals.Controller;

import java.util.ArrayList;
import java.util.logging.Logger;

public class GameClientController implements Controller {
    private static final Logger logger = Logger.getLogger(GameClientController.class.getName());

    private final ConnectionManager connectionManager;

    private Game game;
    private Player player;
    private Player otherPlayer;

    public GameClientController(ConnectionManager existingConnectionManager) {
        connectionManager = existingConnectionManager;
    }

    @Override
    public ArrayList<Class<? extends Command<? extends Receiver>>> commandsList() {
        return new ArrayList<>() {
            {
                add(ConnectedCommand.class);
                add(DisconnectedCommand.class);
                add(JoinGameResponseCommand.class);
                add(LeaveGameResponseCommand.class);
                add(PlacePieceResponseCommand.class);
            }
        };
    }

    public void joinGame() throws JsonProcessingException {
        if (!game.isOpen()) {
            logger.info("Game not open");
            return;
        }

        connectionManager.write(new JoinGameCommand());
    }

    protected void handleJoinGameResponse(boolean joinedGame, Piece piece) {
        logger.info("Joined game: " + joinedGame);

        game = new Game();
        player = new Player(piece);
        otherPlayer = new Player(piece == Piece.X ? Piece.O : Piece.X);

        game.open();
        game.join(player);
        game.join(otherPlayer);
        game.start();
    }

    public void leaveGame() throws JsonProcessingException {
        if (!game.isOpen() && !game.hasStarted()) {
            logger.info("Game not open or started");
            return;
        }

        connectionManager.write(new LeaveGameCommand());
    }

    protected void handleLeaveGameResponse(boolean leftGame) {
        logger.info("Left game: " + leftGame);

        game.leave(player);
        game.close();
    }

    public void placePiece(int row, int column) throws JsonProcessingException {
        if (!game.hasStarted() || !game.isPlayerTurn(player)) {
            logger.info("Not player's turn");
            return;
        }

        connectionManager.write(new PlacePieceCommand(row, column));
    }

    protected void handlePlacePieceResponse(boolean placedPiece, int row, int column) {
        logger.info("Placed piece at (" + row + ", " + column + "): " + placedPiece);

        if (!placedPiece) {
            return;
        }

        game.placePiece(otherPlayer, row, column);
    }

    public void gameOver(boolean hasWinner, Player winner) {
        logger.info("Game over: " + hasWinner + ", winner: " + winner);
    }
}
