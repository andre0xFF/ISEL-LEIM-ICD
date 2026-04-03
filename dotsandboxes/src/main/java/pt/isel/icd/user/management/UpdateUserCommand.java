package pt.isel.icd.user.management;

import java.util.UUID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pt.isel.icd.ServerController;
import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.serialization.XmlHelper;

public class UpdateUserCommand
    implements SimpleSocketCommand<ServerController>
{

    private ServerController receiver;
    private UUID socketId;
    private String nationality;
    private int age;
    private String photo;

    public UpdateUserCommand() {}

    public UpdateUserCommand(String nationality, int age, String photo) {
        this.nationality = nationality;
        this.age = age;
        this.photo = photo;
    }

    public String nationality() {
        return nationality;
    }

    public int age() {
        return age;
    }

    public String photo() {
        return photo;
    }

    @Override
    public String commandName() {
        return "UpdateUserCommand";
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
        receiver.updateUserProfile(socketId, nationality, age, photo);
    }

    @Override
    public void toXml(Document doc, Element el) {
        XmlHelper.addChildElement(doc, el, "nationality", nationality);
        XmlHelper.addChildElement(doc, el, "age", String.valueOf(age));
        XmlHelper.addChildElement(doc, el, "photo", photo);
    }

    @Override
    public void fromXml(Element el) {
        nationality = XmlHelper.getChildText(el, "nationality");
        String ageStr = XmlHelper.getChildText(el, "age");
        age = ageStr != null ? Integer.parseInt(ageStr) : 0;
        photo = XmlHelper.getChildText(el, "photo");
    }
}
