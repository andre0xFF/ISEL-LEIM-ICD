package controllers;

import controllers.commands.CommandController;
import sessions.Communication;
import sessions.Server;

import java.io.IOException;

public class ClientController implements Controller {

    private final Communication communication = new Communication();

    @Override
    public void acceptCommunication() {
        communication.start();
        readCommunication(communication);
    }

    private void readCommunication(Communication communication) {
        new Thread(() -> {
            CommandController command;

            while ((command = communication.readAsCommand()) != null) {
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

    @Override
    public void sendCommand(CommandController command) {
        communication.write(command);
    }
}
