package pt.isel.icd.communication;

import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;

import java.util.logging.Logger;

public class ConnectedCommand implements Command<Receiver> {
    private static final Logger logger = Logger.getLogger(ConnectedCommand.class.getName());

    @Override
    public void setReceiver(Receiver receiver) {}

    @Override
    public void execute() {
        logger.info("Connected");
    }
}
