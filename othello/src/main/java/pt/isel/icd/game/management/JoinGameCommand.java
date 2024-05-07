package pt.isel.icd.game.management;

import pt.isel.icd.patterns.command.Command;

public class JoinGameCommand implements Command<GameServerController> {
    private GameServerController gameServerController;

    @Override
    public void setReceiver(GameServerController existingGameServerController) {
        gameServerController = existingGameServerController;
    }

    @Override
    public void execute() {
        gameServerController.joinGame();
    }
}
