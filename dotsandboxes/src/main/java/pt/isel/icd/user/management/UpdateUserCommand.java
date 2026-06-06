package pt.isel.icd.user.management;

import java.util.UUID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pt.isel.icd.ServerController;
import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.serialization.XmlHelper;

/**
 * Edicao de perfil. Transporta todos os campos editaveis: nome completo,
 * nacionalidade (ISO alfa-2), idade, foto (Base64) e cor de fundo preferida.
 */
public class UpdateUserCommand
    implements SimpleSocketCommand<ServerController>
{

    private ServerController receiver;
    private UUID socketId;
    private String fullName;
    private String nationality;
    private int age;
    private String photo;
    private String preferredColor;

    public UpdateUserCommand() {}

    public UpdateUserCommand(
        String fullName,
        String nationality,
        int age,
        String photo,
        String preferredColor
    ) {
        this.fullName = fullName;
        this.nationality = nationality;
        this.age = age;
        this.photo = photo;
        this.preferredColor = preferredColor;
    }

    public String fullName() {
        return fullName;
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

    public String preferredColor() {
        return preferredColor;
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
        receiver.updateUserProfile(
            socketId,
            fullName,
            nationality,
            age,
            photo,
            preferredColor
        );
    }

    @Override
    public void toXml(Document doc, Element el) {
        XmlHelper.addChildElement(doc, el, "fullName", fullName);
        XmlHelper.addChildElement(doc, el, "nationality", nationality);
        XmlHelper.addChildElement(doc, el, "age", String.valueOf(age));
        XmlHelper.addChildElement(doc, el, "photo", photo);
        XmlHelper.addChildElement(doc, el, "preferredColor", preferredColor);
    }

    @Override
    public void fromXml(Element el) {
        fullName = XmlHelper.getChildText(el, "fullName");
        nationality = XmlHelper.getChildText(el, "nationality");
        String ageStr = XmlHelper.getChildText(el, "age");
        age = ageStr != null ? Integer.parseInt(ageStr) : 0;
        photo = XmlHelper.getChildText(el, "photo");
        preferredColor = XmlHelper.getChildText(el, "preferredColor");
    }
}
