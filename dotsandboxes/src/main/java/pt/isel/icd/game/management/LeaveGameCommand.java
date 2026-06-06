package pt.isel.icd.game.management;

import java.util.UUID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pt.isel.icd.ServerController;
import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.serialization.XmlHelper;

public class LeaveGameCommand implements SimpleSocketCommand<ServerController> {

    private ServerController receiver;
    private UUID socketId;
    private String gameId; // jogo que se pretende abandonar (multi-jogo)

    public LeaveGameCommand() {}

    public LeaveGameCommand(String gameId) {
        this.gameId = gameId;
    }

    public String gameId() {
        return gameId;
    }

    @Override
    public String commandName() {
        return "LeaveGameCommand";
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
        receiver.leaveGame(socketId, gameId);
    }

    @Override
    public void toXml(Document doc, Element el) {
        XmlHelper.addChildElement(doc, el, "gameId", gameId);
    }

    @Override
    public void fromXml(Element el) {
        gameId = XmlHelper.getChildText(el, "gameId");
    }
}
