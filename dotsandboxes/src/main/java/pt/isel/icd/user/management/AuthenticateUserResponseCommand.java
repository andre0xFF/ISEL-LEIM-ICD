package pt.isel.icd.user.management;

import java.util.UUID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pt.isel.icd.ClientController;
import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.serialization.XmlHelper;

public class AuthenticateUserResponseCommand
    implements SimpleSocketCommand<ClientController>
{

    private ClientController receiver;
    private UUID socketId;
    private String username;
    private boolean authenticated;

    public AuthenticateUserResponseCommand() {}

    public AuthenticateUserResponseCommand(
        String username,
        boolean authenticated
    ) {
        this.username = username;
        this.authenticated = authenticated;
    }

    @Override
    public String commandName() {
        return "AuthenticateUserResponseCommand";
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
        receiver.handleAuthenticateUserResponse(username, authenticated);
    }

    @Override
    public void toXml(Document doc, Element el) {
        XmlHelper.addChildElement(doc, el, "username", username);
        XmlHelper.addChildElement(
            doc,
            el,
            "authenticated",
            String.valueOf(authenticated)
        );
    }

    @Override
    public void fromXml(Element el) {
        username = XmlHelper.getChildText(el, "username");
        authenticated = Boolean.parseBoolean(
            XmlHelper.getChildText(el, "authenticated")
        );
    }
}
