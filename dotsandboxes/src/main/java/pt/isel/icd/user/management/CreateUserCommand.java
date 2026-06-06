package pt.isel.icd.user.management;

import java.util.UUID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pt.isel.icd.ServerController;
import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.serialization.XmlHelper;
import pt.isel.icd.user.logic.User;

public class CreateUserCommand
    implements SimpleSocketCommand<ServerController>
{

    private ServerController receiver;
    private UUID socketId;
    private String username;
    private String password;
    private String fullName; // opcional (registo Web); a GUI nao o envia
    private String photo; // opcional (Base64)

    public CreateUserCommand() {}

    public CreateUserCommand(User user) {
        this.username = user.username();
        this.password = user.password();
    }

    public CreateUserCommand(User user, String fullName, String photo) {
        this(user);
        this.fullName = fullName;
        this.photo = photo;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public String fullName() {
        return fullName;
    }

    public String photo() {
        return photo;
    }

    @Override
    public String commandName() {
        return "CreateUserCommand";
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
    public void setReceiver(ServerController r) {
        receiver = r;
    }

    @Override
    public void execute() {
        receiver.createUser(socketId, username, password, fullName, photo);
    }

    @Override
    public void toXml(Document doc, Element el) {
        XmlHelper.addChildElement(doc, el, "username", username);
        XmlHelper.addChildElement(doc, el, "password", password);
        // Campos opcionais: so sao escritos quando presentes (registo Web).
        if (fullName != null) {
            XmlHelper.addChildElement(doc, el, "fullName", fullName);
        }
        if (photo != null) {
            XmlHelper.addChildElement(doc, el, "photo", photo);
        }
    }

    @Override
    public void fromXml(Element el) {
        username = XmlHelper.getChildText(el, "username");
        password = XmlHelper.getChildText(el, "password");
        fullName = XmlHelper.getChildText(el, "fullName");
        photo = XmlHelper.getChildText(el, "photo");
    }
}
