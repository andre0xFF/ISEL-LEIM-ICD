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
    private String fullName;
    private String nationality;
    private int age;
    private String photo;
    private String preferredColor;
    private int wins;
    private int losses;
    private int totalGames;
    private long totalTimeMillis;
    private boolean hasProfile;

    public ReadUserProfileResponseCommand() {}

    public ReadUserProfileResponseCommand(Profile profile, boolean hasProfile) {
        this.hasProfile = hasProfile;
        if (profile != null) {
            this.username = profile.username();
            this.fullName = profile.fullName();
            this.nationality = profile.nationality();
            this.age = profile.age();
            this.photo = profile.photo();
            this.preferredColor = profile.preferredColor();
            this.wins = profile.wins();
            this.losses = profile.losses();
            this.totalGames = profile.totalGames();
            this.totalTimeMillis = profile.totalTimeMillis();
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
            ? new Profile(
                  username,
                  fullName,
                  nationality,
                  age,
                  photo,
                  preferredColor,
                  wins,
                  losses,
                  totalGames,
                  totalTimeMillis
              )
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
            XmlHelper.addChildElement(doc, el, "fullName", fullName);
            XmlHelper.addChildElement(doc, el, "nationality", nationality);
            XmlHelper.addChildElement(doc, el, "age", String.valueOf(age));
            XmlHelper.addChildElement(doc, el, "photo", photo);
            XmlHelper.addChildElement(
                doc,
                el,
                "preferredColor",
                preferredColor
            );
            XmlHelper.addChildElement(doc, el, "wins", String.valueOf(wins));
            XmlHelper.addChildElement(
                doc,
                el,
                "losses",
                String.valueOf(losses)
            );
            XmlHelper.addChildElement(
                doc,
                el,
                "totalGames",
                String.valueOf(totalGames)
            );
            XmlHelper.addChildElement(
                doc,
                el,
                "totalTimeMillis",
                String.valueOf(totalTimeMillis)
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
            fullName = XmlHelper.getChildText(el, "fullName");
            nationality = XmlHelper.getChildText(el, "nationality");
            String ageStr = XmlHelper.getChildText(el, "age");
            age = ageStr != null ? Integer.parseInt(ageStr) : 0;
            photo = XmlHelper.getChildText(el, "photo");
            preferredColor = XmlHelper.getChildText(el, "preferredColor");
            String winsStr = XmlHelper.getChildText(el, "wins");
            wins = winsStr != null ? Integer.parseInt(winsStr) : 0;
            String lossesStr = XmlHelper.getChildText(el, "losses");
            losses = lossesStr != null ? Integer.parseInt(lossesStr) : 0;
            String tgStr = XmlHelper.getChildText(el, "totalGames");
            totalGames = tgStr != null ? Integer.parseInt(tgStr) : 0;
            String ttStr = XmlHelper.getChildText(el, "totalTimeMillis");
            totalTimeMillis = ttStr != null ? Long.parseLong(ttStr) : 0L;
        }
    }
}
