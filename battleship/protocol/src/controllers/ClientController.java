package controllers;

import behavioral.command.Command;
import controllers.commands.ClientControllerCommand;
import controllers.commands.CommandController;
import sessions.Communication;

import java.io.IOException;

public class ClientController implements Controller {

    private final Communication communication = new Communication();

    @Override
    public void acceptCommunication() {
        communication.start();
        readCommunication(communication);
    }

    @Override
    public void sendCommand(Command<?> command) {
        communication.write(command);
    }

    private void readCommunication(Communication communication) {
        new Thread(() -> {
            ClientControllerCommand command;

            while ((command = (ClientControllerCommand) communication.readAsCommand()) != null) {
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
