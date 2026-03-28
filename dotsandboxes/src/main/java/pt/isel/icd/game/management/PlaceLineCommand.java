package pt.isel.icd.game.management;

import java.util.UUID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pt.isel.icd.ServerController;
import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.game.logic.Line;
import pt.isel.icd.serialization.CommandSerializer;
import pt.isel.icd.serialization.XmlHelper;

public class PlaceLineCommand
    implements
        SimpleSocketCommand<ServerController>,
        CommandSerializer.XmlSerializable
{

    private ServerController receiver;
    private UUID socketId;
    private int row;
    private int col;
    private String orientation; // "HORIZONTAL" or "VERTICAL"

    public PlaceLineCommand() {}

    public PlaceLineCommand(int row, int col, Line.Orientation orientation) {
        this.row = row;
        this.col = col;
        this.orientation = orientation.name();
    }

    public int row() {
        return row;
    }

    public int col() {
        return col;
    }

    public Line.Orientation orientation() {
        return Line.Orientation.valueOf(orientation);
    }

    @Override
    public String commandName() {
        return "PlaceLineCommand";
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
        receiver.placeLine(
            socketId,
            row,
            col,
            Line.Orientation.valueOf(orientation)
        );
    }

    @Override
    public void toXml(Document doc, Element el) {
        XmlHelper.addChildElement(doc, el, "row", String.valueOf(row));
        XmlHelper.addChildElement(doc, el, "col", String.valueOf(col));
        XmlHelper.addChildElement(doc, el, "orientation", orientation);
    }

    @Override
    public void fromXml(Element el) {
        row = Integer.parseInt(XmlHelper.getChildText(el, "row"));
        col = Integer.parseInt(XmlHelper.getChildText(el, "col"));
        orientation = XmlHelper.getChildText(el, "orientation");
    }
}
