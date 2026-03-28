package pt.isel.icd.user.management;

import java.util.UUID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pt.isel.icd.ClientController;
import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.serialization.CommandSerializer;
import pt.isel.icd.serialization.XmlHelper;

public class CreateUserResponseCommand
    implements
        SimpleSocketCommand<ClientController>,
        CommandSerializer.XmlSerializable
{

    private ClientController receiver;
    private UUID socketId;
    private String username;
    private boolean created;

    public CreateUserResponseCommand() {}

    public CreateUserResponseCommand(String username, boolean created) {
        this.username = username;
        this.created = created;
    }

    @Override
    public String commandName() {
        return "CreateUserResponseCommand";
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
        receiver.handleCreateUserResponse(username, created);
    }

    @Override
    public void toXml(Document doc, Element el) {
        XmlHelper.addChildElement(doc, el, "username", username);
        XmlHelper.addChildElement(doc, el, "created", String.valueOf(created));
    }

    @Override
    public void fromXml(Element el) {
        username = XmlHelper.getChildText(el, "username");
        created = Boolean.parseBoolean(XmlHelper.getChildText(el, "created"));
    }
}
