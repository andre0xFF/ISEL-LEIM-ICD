package pt.isel.icd.game.management;

import java.util.UUID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pt.isel.icd.ServerController;
import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.game.logic.Dot;
import pt.isel.icd.serialization.XmlHelper;

public class PlaceLineCommand implements SimpleSocketCommand<ServerController> {

    private ServerController receiver;
    private UUID socketId;
    private int dot1Row;
    private int dot1Col;
    private int dot2Row;
    private int dot2Col;
    private String gameId; // jogo a que a jogada se destina (multi-jogo)

    public PlaceLineCommand() {}

    public PlaceLineCommand(String gameId, Dot dot1, Dot dot2) {
        this.gameId = gameId;
        this.dot1Row = dot1.row();
        this.dot1Col = dot1.col();
        this.dot2Row = dot2.row();
        this.dot2Col = dot2.col();
    }

    public String gameId() {
        return gameId;
    }

    public Dot dot1() {
        return new Dot(dot1Row, dot1Col);
    }

    public Dot dot2() {
        return new Dot(dot2Row, dot2Col);
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
        receiver.placeLine(socketId, gameId, dot1(), dot2());
    }

    @Override
    public void toXml(Document doc, Element el) {
        XmlHelper.addChildElement(doc, el, "dot1Row", String.valueOf(dot1Row));
        XmlHelper.addChildElement(doc, el, "dot1Col", String.valueOf(dot1Col));
        XmlHelper.addChildElement(doc, el, "dot2Row", String.valueOf(dot2Row));
        XmlHelper.addChildElement(doc, el, "dot2Col", String.valueOf(dot2Col));
        XmlHelper.addChildElement(doc, el, "gameId", gameId);
    }

    @Override
    public void fromXml(Element el) {
        dot1Row = Integer.parseInt(XmlHelper.getChildText(el, "dot1Row"));
        dot1Col = Integer.parseInt(XmlHelper.getChildText(el, "dot1Col"));
        dot2Row = Integer.parseInt(XmlHelper.getChildText(el, "dot2Row"));
        dot2Col = Integer.parseInt(XmlHelper.getChildText(el, "dot2Col"));
        gameId = XmlHelper.getChildText(el, "gameId");
    }
}
