package pt.isel.icd.communication;

import java.util.UUID;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DisconnectedCommand implements SimpleSocketCommand<Object> {

    private static final Logger logger = Logger.getLogger(
        DisconnectedCommand.class.getName()
    );

    private UUID socketId;

    @Override
    public String commandName() {
        return "DisconnectedCommand";
    }

    @Override
    public UUID socketId() {
        return socketId;
    }

    @Override
    public void socketId(UUID socketId) {
        this.socketId = socketId;
    }

    @Override
    public boolean requiresAuthentication() {
        return false;
    }

    @Override
    public void setReceiver(Object receiver) {}

    @Override
    public void execute() {
        logger.info("Disconnected");
    }

    @Override
    public void toXml(Document doc, Element element) {}

    @Override
    public void fromXml(Element element) {}
}
