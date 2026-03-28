package pt.isel.icd.communication;

import java.util.logging.Logger;

public class DisconnectedCommand implements Command<Object> {

    private static final Logger logger = Logger.getLogger(
        DisconnectedCommand.class.getName()
    );

    @Override
    public void setReceiver(Object receiver) {}

    @Override
    public void execute() {
        logger.info("Disconnected");
    }
}
