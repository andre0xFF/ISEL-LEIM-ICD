package pt.isel.icd.game.management;

import java.util.UUID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pt.isel.icd.ClientController;
import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.game.logic.PlayerMarker;
import pt.isel.icd.serialization.XmlHelper;

public class JoinGameResponseCommand
    implements SimpleSocketCommand<ClientController>
{

    private ClientController receiver;
    private UUID socketId;
    private boolean joined;
    private String marker; // "A" or "B"
    private int boardRows;
    private int boardCols;

    public JoinGameResponseCommand() {}

    public JoinGameResponseCommand(
        boolean joined,
        PlayerMarker marker,
        int boardRows,
        int boardCols
    ) {
        this.joined = joined;
        this.marker = marker != null ? marker.name() : null;
        this.boardRows = boardRows;
        this.boardCols = boardCols;
    }

    @Override
    public String commandName() {
        return "JoinGameResponseCommand";
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
        receiver.handleJoinGameResponse(
            joined,
            PlayerMarker.valueOf(marker),
            boardRows,
            boardCols
        );
    }

    @Override
    public void toXml(Document doc, Element el) {
        XmlHelper.addChildElement(doc, el, "joined", String.valueOf(joined));
        XmlHelper.addChildElement(doc, el, "marker", marker);
        XmlHelper.addChildElement(
            doc,
            el,
            "boardRows",
            String.valueOf(boardRows)
        );
        XmlHelper.addChildElement(
            doc,
            el,
            "boardCols",
            String.valueOf(boardCols)
        );
    }

    @Override
    public void fromXml(Element el) {
        joined = Boolean.parseBoolean(XmlHelper.getChildText(el, "joined"));
        marker = XmlHelper.getChildText(el, "marker");
        String r = XmlHelper.getChildText(el, "boardRows");
        boardRows = r != null ? Integer.parseInt(r) : 4;
        String c = XmlHelper.getChildText(el, "boardCols");
        boardCols = c != null ? Integer.parseInt(c) : 4;
    }
}
