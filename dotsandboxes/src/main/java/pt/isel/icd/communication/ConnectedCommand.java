package pt.isel.icd.communication;

import java.util.UUID;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConnectedCommand implements SimpleSocketCommand<Object> {

    private static final Logger logger = Logger.getLogger(
        ConnectedCommand.class.getName()
    );

    private UUID socketId;

    @Override
    public UUID socketId() {
        return socketId;
    }

    @Override
    public void socketId(UUID socketId) {
        this.socketId = socketId;
    }

    @Override
    public String commandName() {
        return "ConnectedCommand";
    }

    @Override
    public boolean requiresAuthentication() {
        return false;
    }

    @Override
    public void setReceiver(Object receiver) {}

    @Override
    public void execute() {
        logger.info("Connected");
    }

    @Override
    public void toXml(Document doc, Element element) {}

    @Override
    public void fromXml(Element element) {}
}
