package pt.isel.icd.communication;

import java.io.IOException;
import java.util.logging.Logger;
import org.xml.sax.SAXException;

public class ClientHandler implements Runnable {

    private static final Logger logger = Logger.getLogger(
        ClientHandler.class.getName()
    );

    private final SimpleSocket simpleSocket;
    private final SimpleSocketManager simpleSocketManager;

    public ClientHandler(SimpleSocketManager manager, SimpleSocket socket) {
        simpleSocketManager = manager;
        simpleSocket = socket;
        simpleSocketManager.connectClient(simpleSocket);
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void run() {
        simpleSocketManager.route(new ConnectedCommand());

        try {
            while (simpleSocket.isConnected()) {
                try {
                    SimpleSocketCommand<?> command = simpleSocket.read();

                    if (command == null) {
                        handleDisconnect();
                        return;
                    } else {
                        command.socketId(simpleSocket.identifier());
                        simpleSocketManager.route(command);
                    }
                } catch (SAXException e) {
                    logger.warning("XML validation failed: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            handleDisconnect();
        }
    }

    /**
     * Encaminha um DisconnectedCommand (com o id do socket) para que o
     * controlador possa limpar o estado associado, retira o socket do gestor e
     * fecha-o.
     */
    private void handleDisconnect() {
        DisconnectedCommand disconnected = new DisconnectedCommand();
        disconnected.socketId(simpleSocket.identifier());
        simpleSocketManager.route(disconnected);
        simpleSocketManager.disconnectClient(simpleSocket);
        try {
            simpleSocket.close();
        } catch (IOException ignored) {
            // fecho best-effort
        }
    }
}
