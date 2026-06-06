package pt.isel.icd.user.management;

import java.util.UUID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pt.isel.icd.ServerController;
import pt.isel.icd.communication.SimpleSocketCommand;

/**
 * Pedido do quadro de honra (ranking de jogadores). Sem campos. E informacao
 * publica/agregada, por isso nao exige autenticacao.
 */
public class HonorBoardCommand
    implements SimpleSocketCommand<ServerController>
{

    private ServerController receiver;
    private UUID socketId;

    @Override
    public boolean requiresAuthentication() {
        return false;
    }

    @Override
    public String commandName() {
        return "HonorBoardCommand";
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
        receiver.honorBoard(socketId);
    }

    @Override
    public void toXml(Document doc, Element el) {
        /* sem campos */
    }

    @Override
    public void fromXml(Element el) {
        /* sem campos */
    }
}
