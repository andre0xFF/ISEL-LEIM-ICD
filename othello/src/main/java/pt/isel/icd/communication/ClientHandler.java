package pt.isel.icd.communication;

import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;

import java.io.IOException;

public class ClientHandler implements Runnable {
    private final SimpleSocket simpleSocket;
    private final SimpleSocketManager simpleSocketManager;
    private final Thread thread;

    public ClientHandler(SimpleSocketManager existingSimpleSocketManager, SimpleSocket existingSimpleSocket) {
        simpleSocketManager = existingSimpleSocketManager;
        simpleSocket = existingSimpleSocket;

        simpleSocketManager.connectClient(simpleSocket);

        thread = new Thread(this);
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
                // TODO convert to command
                // Command<Receiver> command = Command.fromXml(line);
                // command.setOriginSimpleSocket(simpleSocket);
                Command<Receiver> command = null;

                simpleSocketManager.route(command);
            }
        } catch (IOException e) {
            simpleSocketManager.route(new DisconnectCommand());

            throw new RuntimeException(e);
        }
    }
}
