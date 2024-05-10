package pt.isel.icd.communication;

import pt.isel.icd.patterns.command.Receiver;
import pt.isel.icd.serialization.Serializer;

import java.io.IOException;

public class ClientHandler implements Runnable {
    private final SimpleSocket simpleSocket;
    private final SimpleSocketManager simpleSocketManager;
    private final Serializer serializer;

    public ClientHandler(
            SimpleSocketManager existingSimpleSocketManager,
            SimpleSocket existingSimpleSocket,
            Serializer existingSerializer
    ) {
        simpleSocketManager = existingSimpleSocketManager;
        simpleSocket = existingSimpleSocket;
        serializer = existingSerializer;

        simpleSocketManager.connectClient(simpleSocket);

        Thread thread = new Thread(this);

        thread.start();
    }

    @Override
    public void run() {
        simpleSocketManager.route(new ConnectedCommand());

        try {
            SimpleSocketCommand<Receiver> simpleSocketCommand = simpleSocket.read();

            if (simpleSocketCommand == null) {
                simpleSocketManager.route(new DisconnectedCommand());

                simpleSocket.close();
            }
            else {
                simpleSocketCommand.connectionIdentifier(simpleSocket.identifier());

                simpleSocketManager.route(simpleSocketCommand);
            }
        } catch (IOException e) {
            simpleSocketManager.route(new DisconnectedCommand());

            throw new RuntimeException(e);
        }
    }
}
