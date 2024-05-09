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
        simpleSocketManager.route(new ConnectCommand());

        try {
            String line = simpleSocket.readLine();

            // The end of the stream has been reached.
            if (line == null) {
                simpleSocketManager.route(new DisconnectCommand());
                simpleSocket.close();
            }
            else {
                ConnectionCommand<Receiver> command = serializer.deserialize(line, ConnectionCommand.class);

                command.connectionIdentifier(simpleSocket.identifier());

                simpleSocketManager.route(command);
            }
        } catch (IOException e) {
            simpleSocketManager.route(new DisconnectCommand());

            throw new RuntimeException(e);
        }
    }
}
