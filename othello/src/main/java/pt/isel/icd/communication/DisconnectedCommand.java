package pt.isel.icd.communication;

import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;

import java.util.logging.Logger;

public class DisconnectedCommand implements Command<Receiver> {
    private static final Logger logger = Logger.getLogger(DisconnectedCommand.class.getName());

    @Override
    public void setReceiver(Receiver receiver) {

    }

    @Override
    public void execute() {
        logger.info("Disconnected");
    }
}
