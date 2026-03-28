package pt.isel.icd.communication;

import java.io.IOException;

public class ClientHandler implements Runnable {

    private final SimpleSocket simpleSocket;
    private final SimpleSocketManager simpleSocketManager;

    public ClientHandler(SimpleSocketManager manager, SimpleSocket socket) {
        simpleSocketManager = manager;
        simpleSocket = socket;
        simpleSocketManager.connectClient(simpleSocket);
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        simpleSocketManager.route(new ConnectedCommand());

        try {
            while (simpleSocket.isConnected()) {
                SimpleSocketCommand<?> command = simpleSocket.read();

                if (command == null) {
                    simpleSocketManager.route(new DisconnectedCommand());
                    simpleSocket.close();
                } else {
                    command.socketId(simpleSocket.identifier());
                    simpleSocketManager.route(command);
                }
            }
        } catch (IOException e) {
            simpleSocketManager.route(new DisconnectedCommand());
            throw new RuntimeException(e);
        }
    }
}
