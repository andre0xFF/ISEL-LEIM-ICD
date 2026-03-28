package pt.isel.icd.game.management;

import java.util.UUID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pt.isel.icd.ClientController;
import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.serialization.CommandSerializer;
import pt.isel.icd.serialization.XmlHelper;

public class LeaveGameResponseCommand
    implements
        SimpleSocketCommand<ClientController>,
        CommandSerializer.XmlSerializable
{

    private ClientController receiver;
    private UUID socketId;
    private boolean left;

    public LeaveGameResponseCommand() {}

    public LeaveGameResponseCommand(boolean left) {
        this.left = left;
    }

    @Override
    public String commandName() {
        return "LeaveGameResponseCommand";
    }

    @Override
    public boolean requiresAuthentication() {
        return false;
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
    public void setReceiver(ClientController r) {
        receiver = r;
    }

    @Override
    public void execute() {
        receiver.handleLeaveGameResponse(left);
    }

    @Override
    public void toXml(Document doc, Element el) {
        XmlHelper.addChildElement(doc, el, "left", String.valueOf(left));
    }

    @Override
    public void fromXml(Element el) {
        left = Boolean.parseBoolean(XmlHelper.getChildText(el, "left"));
    }
}
