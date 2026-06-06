package pt.isel.icd.user.management;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import pt.isel.icd.ClientController;
import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.serialization.XmlHelper;
import pt.isel.icd.user.logic.Profile;

/**
 * Quadro de honra ordenado (uma entrada por jogador). Consumido sobretudo pelo
 * cliente Web (parse do XML em JavaScript: fotos em Base64, bandeiras a partir
 * do codigo ISO). Cada entrada traz os campos necessarios ao ecra.
 */
public class HonorBoardResponseCommand
    implements SimpleSocketCommand<ClientController>
{

    private ClientController receiver;
    private UUID socketId;
    private final List<Profile> entries = new ArrayList<>();

    public HonorBoardResponseCommand() {}

    public HonorBoardResponseCommand(List<Profile> orderedProfiles) {
        this.entries.addAll(orderedProfiles);
    }

    @Override
    public boolean requiresAuthentication() {
        return false;
    }

    @Override
    public String commandName() {
        return "HonorBoardResponseCommand";
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
        receiver.handleHonorBoardResponse(entries.size());
    }

    @Override
    public void toXml(Document doc, Element el) {
        for (Profile p : entries) {
            Element entry = doc.createElement("entry");
            el.appendChild(entry);
            XmlHelper.addChildElement(doc, entry, "username", p.username());
            XmlHelper.addChildElement(doc, entry, "fullName", p.fullName());
            XmlHelper.addChildElement(
                doc,
                entry,
                "nationality",
                p.nationality()
            );
            XmlHelper.addChildElement(doc, entry, "photo", p.photo());
            XmlHelper.addChildElement(
                doc,
                entry,
                "wins",
                String.valueOf(p.wins())
            );
            XmlHelper.addChildElement(
                doc,
                entry,
                "losses",
                String.valueOf(p.losses())
            );
            XmlHelper.addChildElement(
                doc,
                entry,
                "totalGames",
                String.valueOf(p.totalGames())
            );
            XmlHelper.addChildElement(
                doc,
                entry,
                "totalTimeMillis",
                String.valueOf(p.totalTimeMillis())
            );
        }
    }

    @Override
    public void fromXml(Element el) {
        entries.clear();
        NodeList nodes = el.getElementsByTagName("entry");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element entry = (Element) nodes.item(i);
            entries.add(
                new Profile(
                    XmlHelper.getChildText(entry, "username"),
                    XmlHelper.getChildText(entry, "fullName"),
                    XmlHelper.getChildText(entry, "nationality"),
                    parseInt(XmlHelper.getChildText(entry, "age")),
                    XmlHelper.getChildText(entry, "photo"),
                    XmlHelper.getChildText(entry, "preferredColor"),
                    parseInt(XmlHelper.getChildText(entry, "wins")),
                    parseInt(XmlHelper.getChildText(entry, "losses")),
                    parseInt(XmlHelper.getChildText(entry, "totalGames")),
                    parseLong(XmlHelper.getChildText(entry, "totalTimeMillis"))
                )
            );
        }
    }

    private static int parseInt(String value) {
        try {
            return value != null ? Integer.parseInt(value.trim()) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static long parseLong(String value) {
        try {
            return value != null ? Long.parseLong(value.trim()) : 0L;
        } catch (NumberFormatException e) {
            return 0L;
        }
    }
}
