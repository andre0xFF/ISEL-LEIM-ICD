package pt.isel.icd.game.management;

import java.util.UUID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pt.isel.icd.ServerController;
import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.serialization.CommandSerializer;

public class LeaveGameCommand
    implements
        SimpleSocketCommand<ServerController>,
        CommandSerializer.XmlSerializable
{

    private ServerController receiver;
    private UUID socketId;

    @Override
    public String commandName() {
        return "LeaveGameCommand";
    }

    @Override
    public UUID socketId() {
        return socketId;
    }

    @Override
    public void socketId(UUID id) {
        socketId = id;
    }

    @Override
    public void setReceiver(ServerController r) {
        receiver = r;
    }

    @Override
    public void execute() {
        receiver.leaveGame(socketId);
    }

    @Override
    public void toXml(Document doc, Element el) {
        /* no fields */
    }

    @Override
    public void fromXml(Element el) {
        /* no fields */
    }
}
