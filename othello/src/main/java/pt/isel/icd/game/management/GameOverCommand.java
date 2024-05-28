package pt.isel.icd.game.management;

import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.game.logic.Player;

import java.util.UUID;

public class GameOverCommand implements SimpleSocketCommand<GameClientController> {
    private final boolean hasWinner;
    private final Player winner;
    private UUID socketId;
    private GameClientController gameClientController;

    public GameOverCommand(boolean hasWinner, Player winner) {
        this.hasWinner = hasWinner;
        this.winner = winner;
    }

    @Override
    public UUID socketId() {
        return socketId;
    }

    @Override
    public void socketId(UUID existingSocketId) {
        socketId = existingSocketId;
    }

    @Override
    public void setReceiver(GameClientController gameClientController) {
        this.gameClientController = gameClientController;
    }

    @Override
    public void execute() {
        gameClientController.gameOver(hasWinner, winner);
    }
}
