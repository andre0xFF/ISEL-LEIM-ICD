package pt.isel.icd.communication;

import java.util.logging.Logger;

public class ConnectedCommand implements Command<Object> {

    private static final Logger logger = Logger.getLogger(
        ConnectedCommand.class.getName()
    );

    @Override
    public void setReceiver(Object receiver) {}

    @Override
    public void execute() {
        logger.info("Connected");
    }
}
