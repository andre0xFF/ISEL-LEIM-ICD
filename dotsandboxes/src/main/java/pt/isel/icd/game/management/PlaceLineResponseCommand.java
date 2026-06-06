package pt.isel.icd.game.management;

import java.util.UUID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pt.isel.icd.ClientController;
import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.game.logic.Dot;
import pt.isel.icd.serialization.XmlHelper;

public class PlaceLineResponseCommand
    implements SimpleSocketCommand<ClientController>
{

    private ClientController receiver;
    private UUID socketId;
    private boolean placed;
    private int dot1Row;
    private int dot1Col;
    private int dot2Row;
    private int dot2Col;
    private int boxesClosed;
    private String marker; // who placed the line
    private boolean extraTurn;
    private String gameId; // jogo a que a resposta se refere (multi-jogo)

    public PlaceLineResponseCommand() {}

    public PlaceLineResponseCommand(
        boolean placed,
        Dot dot1,
        Dot dot2,
        int boxesClosed,
        String marker,
        boolean extraTurn,
        String gameId
    ) {
        this.placed = placed;
        this.dot1Row = dot1.row();
        this.dot1Col = dot1.col();
        this.dot2Row = dot2.row();
        this.dot2Col = dot2.col();
        this.boxesClosed = boxesClosed;
        this.marker = marker;
        this.extraTurn = extraTurn;
        this.gameId = gameId;
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
            new Dot(dot1Row, dot1Col),
            new Dot(dot2Row, dot2Col),
            boxesClosed,
            marker,
            extraTurn,
            gameId
        );
    }

    @Override
    public void toXml(Document doc, Element el) {
        XmlHelper.addChildElement(doc, el, "placed", String.valueOf(placed));
        XmlHelper.addChildElement(doc, el, "dot1Row", String.valueOf(dot1Row));
        XmlHelper.addChildElement(doc, el, "dot1Col", String.valueOf(dot1Col));
        XmlHelper.addChildElement(doc, el, "dot2Row", String.valueOf(dot2Row));
        XmlHelper.addChildElement(doc, el, "dot2Col", String.valueOf(dot2Col));
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
        XmlHelper.addChildElement(doc, el, "gameId", gameId);
    }

    @Override
    public void fromXml(Element el) {
        placed = Boolean.parseBoolean(XmlHelper.getChildText(el, "placed"));
        dot1Row = Integer.parseInt(XmlHelper.getChildText(el, "dot1Row"));
        dot1Col = Integer.parseInt(XmlHelper.getChildText(el, "dot1Col"));
        dot2Row = Integer.parseInt(XmlHelper.getChildText(el, "dot2Row"));
        dot2Col = Integer.parseInt(XmlHelper.getChildText(el, "dot2Col"));
        String bc = XmlHelper.getChildText(el, "boxesClosed");
        boxesClosed = bc != null ? Integer.parseInt(bc) : 0;
        marker = XmlHelper.getChildText(el, "marker");
        extraTurn = Boolean.parseBoolean(
            XmlHelper.getChildText(el, "extraTurn")
        );
        gameId = XmlHelper.getChildText(el, "gameId");
    }
}
