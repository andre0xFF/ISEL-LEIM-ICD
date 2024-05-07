package pt.isel.icd.communication;

import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;

import java.io.IOException;

public class ClientHandler implements Runnable {
    private final SimpleSocket simpleSocket;
    private final SocketService socketService;
    private final Thread thread;

    public ClientHandler(SocketService existingSocketService, SimpleSocket existingSimpleSocket) {
        socketService = existingSocketService;
        simpleSocket = existingSimpleSocket;

        socketService.connectClient(simpleSocket);

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        socketService.route(new ConnectCommand());

        try {
            String line = simpleSocket.readLine();

            // The end of the stream has been reached.
            if (line == null) {
                socketService.route(new DisconnectCommand());

                simpleSocket.close();
            }
            else {
                // TODO convert to command
                Command<Receiver> command = Command.fromXml(line);
                // command.setOriginSimpleSocket(simpleSocket);

                socketService.route(command);
            }
        } catch (IOException e) {
            socketService.route(new DisconnectCommand());

            throw new RuntimeException(e);
        }
    }
}
