package controllers;

import behavioral.command.Command;
import controllers.commands.CommandController;
import controllers.commands.ServerControllerCommand;
import sessions.Communication;
import sessions.Server;

import java.io.IOException;

public class ServerController implements Controller {

    private Server server;
    private Communication communication;

    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public void acceptCommunication() {
        new Thread(() -> {
            server.start();

            while (true) {
                communication = server.accept();
                readCommunication(communication);
            }
        }).start();
    }

    @Override
    public void sendCommand(Command<?> command) {
        communication.write(command);
    }

    private void readCommunication(Communication communication) {
        new Thread(() -> {
            ServerControllerCommand command;

            while ((command = (ServerControllerCommand) communication.readAsCommand()) != null) {
                command.setController(this);
                command.execute();
            }

            try {
                communication.stop();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
