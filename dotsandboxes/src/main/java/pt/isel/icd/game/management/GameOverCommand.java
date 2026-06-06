package pt.isel.icd.game.management;

import java.util.UUID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pt.isel.icd.ClientController;
import pt.isel.icd.communication.SimpleSocketCommand;
import pt.isel.icd.serialization.XmlHelper;

public class GameOverCommand implements SimpleSocketCommand<ClientController> {

    private ClientController receiver;
    private UUID socketId;
    private boolean hasWinner;
    private String winnerMarker;
    private int scoreA;
    private int scoreB;
    private String gameId; // jogo terminado (multi-jogo)
    private String reason; // motivo do fim: "COMPLETED" ou "TIMEOUT"

    public GameOverCommand() {}

    public GameOverCommand(
        boolean hasWinner,
        String winnerMarker,
        int scoreA,
        int scoreB,
        String gameId,
        String reason
    ) {
        this.hasWinner = hasWinner;
        this.winnerMarker = winnerMarker;
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.gameId = gameId;
        this.reason = reason;
    }

    @Override
    public String commandName() {
        return "GameOverCommand";
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
        receiver.handleGameOver(
            hasWinner,
            winnerMarker,
            scoreA,
            scoreB,
            gameId,
            reason
        );
    }

    @Override
    public void toXml(Document doc, Element el) {
        XmlHelper.addChildElement(
            doc,
            el,
            "hasWinner",
            String.valueOf(hasWinner)
        );
        XmlHelper.addChildElement(doc, el, "winnerMarker", winnerMarker);
        XmlHelper.addChildElement(doc, el, "scoreA", String.valueOf(scoreA));
        XmlHelper.addChildElement(doc, el, "scoreB", String.valueOf(scoreB));
        XmlHelper.addChildElement(doc, el, "gameId", gameId);
        XmlHelper.addChildElement(doc, el, "reason", reason);
    }

    @Override
    public void fromXml(Element el) {
        hasWinner = Boolean.parseBoolean(
            XmlHelper.getChildText(el, "hasWinner")
        );
        winnerMarker = XmlHelper.getChildText(el, "winnerMarker");
        String sa = XmlHelper.getChildText(el, "scoreA");
        scoreA = sa != null ? Integer.parseInt(sa) : 0;
        String sb = XmlHelper.getChildText(el, "scoreB");
        scoreB = sb != null ? Integer.parseInt(sb) : 0;
        gameId = XmlHelper.getChildText(el, "gameId");
        reason = XmlHelper.getChildText(el, "reason");
    }
}
