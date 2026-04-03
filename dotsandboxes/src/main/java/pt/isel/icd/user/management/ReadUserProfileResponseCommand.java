package pt.isel.icd.user.management;

import java.util.UUID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pt.isel.icd.ClientController;
import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.serialization.XmlHelper;
import pt.isel.icd.user.logic.Profile;

public class ReadUserProfileResponseCommand
    implements SimpleSocketCommand<ClientController>
{

    private ClientController receiver;
    private UUID socketId;
    private String username;
    private String nationality;
    private int age;
    private String photo;
    private int wins;
    private int losses;
    private boolean hasProfile;

    public ReadUserProfileResponseCommand() {}

    public ReadUserProfileResponseCommand(Profile profile, boolean hasProfile) {
        this.hasProfile = hasProfile;
        if (profile != null) {
            this.username = profile.username();
            this.nationality = profile.nationality();
            this.age = profile.age();
            this.photo = profile.photo();
            this.wins = profile.wins();
            this.losses = profile.losses();
        }
    }

    @Override
    public String commandName() {
        return "ReadUserProfileResponseCommand";
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
        Profile profile = hasProfile
            ? new Profile(username, nationality, age, photo, wins, losses)
            : null;
        receiver.handleReadUserProfileResponse(profile, hasProfile);
    }

    @Override
    public void toXml(Document doc, Element el) {
        XmlHelper.addChildElement(
            doc,
            el,
            "hasProfile",
            String.valueOf(hasProfile)
        );
        if (hasProfile) {
            XmlHelper.addChildElement(doc, el, "username", username);
            XmlHelper.addChildElement(doc, el, "nationality", nationality);
            XmlHelper.addChildElement(doc, el, "age", String.valueOf(age));
            XmlHelper.addChildElement(doc, el, "photo", photo);
            XmlHelper.addChildElement(doc, el, "wins", String.valueOf(wins));
            XmlHelper.addChildElement(
                doc,
                el,
                "losses",
                String.valueOf(losses)
            );
        }
    }

    @Override
    public void fromXml(Element el) {
        hasProfile = Boolean.parseBoolean(
            XmlHelper.getChildText(el, "hasProfile")
        );
        if (hasProfile) {
            username = XmlHelper.getChildText(el, "username");
            nationality = XmlHelper.getChildText(el, "nationality");
            String ageStr = XmlHelper.getChildText(el, "age");
            age = ageStr != null ? Integer.parseInt(ageStr) : 0;
            photo = XmlHelper.getChildText(el, "photo");
            String winsStr = XmlHelper.getChildText(el, "wins");
            wins = winsStr != null ? Integer.parseInt(winsStr) : 0;
            String lossesStr = XmlHelper.getChildText(el, "losses");
            losses = lossesStr != null ? Integer.parseInt(lossesStr) : 0;
        }
    }
}
