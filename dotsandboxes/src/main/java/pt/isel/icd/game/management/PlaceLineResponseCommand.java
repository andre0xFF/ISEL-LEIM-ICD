package pt.isel.icd.game.management;

import java.util.UUID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pt.isel.icd.ClientController;
import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.game.logic.Line;
import pt.isel.icd.serialization.XmlHelper;

public class PlaceLineResponseCommand
    implements SimpleSocketCommand<ClientController>
{

    private ClientController receiver;
    private UUID socketId;
    private boolean placed;
    private int row;
    private int col;
    private String orientation;
    private int boxesClosed;
    private String marker; // who placed the line
    private boolean extraTurn;

    public PlaceLineResponseCommand() {}

    public PlaceLineResponseCommand(
        boolean placed,
        int row,
        int col,
        Line.Orientation orientation,
        int boxesClosed,
        String marker,
        boolean extraTurn
    ) {
        this.placed = placed;
        this.row = row;
        this.col = col;
        this.orientation = orientation.name();
        this.boxesClosed = boxesClosed;
        this.marker = marker;
        this.extraTurn = extraTurn;
    }

    @Override
    public String commandName() {
        return "PlaceLineResponseCommand";
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
        receiver.handlePlaceLineResponse(
            placed,
            row,
            col,
            Line.Orientation.valueOf(orientation),
            boxesClosed,
            marker,
            extraTurn
        );
    }

    @Override
    public void toXml(Document doc, Element el) {
        XmlHelper.addChildElement(doc, el, "placed", String.valueOf(placed));
        XmlHelper.addChildElement(doc, el, "row", String.valueOf(row));
        XmlHelper.addChildElement(doc, el, "col", String.valueOf(col));
        XmlHelper.addChildElement(doc, el, "orientation", orientation);
        XmlHelper.addChildElement(
            doc,
            el,
            "boxesClosed",
            String.valueOf(boxesClosed)
        );
        XmlHelper.addChildElement(doc, el, "marker", marker);
        XmlHelper.addChildElement(
            doc,
            el,
            "extraTurn",
            String.valueOf(extraTurn)
        );
    }

    @Override
    public void fromXml(Element el) {
        placed = Boolean.parseBoolean(XmlHelper.getChildText(el, "placed"));
        row = Integer.parseInt(XmlHelper.getChildText(el, "row"));
        col = Integer.parseInt(XmlHelper.getChildText(el, "col"));
        orientation = XmlHelper.getChildText(el, "orientation");
        String bc = XmlHelper.getChildText(el, "boxesClosed");
        boxesClosed = bc != null ? Integer.parseInt(bc) : 0;
        marker = XmlHelper.getChildText(el, "marker");
        extraTurn = Boolean.parseBoolean(
            XmlHelper.getChildText(el, "extraTurn")
        );
    }
}
