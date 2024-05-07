package pt.isel.icd.communication;

import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.game.management.GameClientController;

public class PingCommand implements Command<GameClientController> {
    @Override
    public void setReceiver(GameClientController receiver) {

    }

    @Override
    public void execute() {

    }
}
