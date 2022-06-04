package controllers;

import controllers.commands.CommandController;
import controllers.commands.ServerControllerCommand;
import sessions.Messenger;
import sessions.Server;

import java.io.IOException;

public class ServerController implements Controller {

    private Server server;
    private Messenger messenger;

    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public void acceptCommunication() {
        new Thread(() -> {
            server.start();

            while (true) {
                messenger = server.accept();
                readCommunication(messenger);
            }
        }).start();
    }

    @Override
    public void sendCommand(CommandController command) {
        messenger.write(command);
    }

    private void readCommunication(Messenger messenger) {
        new Thread(() -> {
            ServerControllerCommand command;

            while ((command = (ServerControllerCommand) messenger.readAsCommand()) != null) {
                command.setController(this);
                command.execute();
            }

            try {
                messenger.stop();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
