package controllers;

import controllers.commands.ClientControllerCommand;
import controllers.commands.CommandController;
import sessions.Messenger;

import java.io.IOException;

public class ClientController implements Controller {

    private final Messenger messenger = new Messenger();

    @Override
    public void acceptCommunication() {
        messenger.start();
        readCommunication(messenger);
    }

    @Override
    public void sendCommand(CommandController command) {
        messenger.write(command);
    }

    private void readCommunication(Messenger messenger) {
        new Thread(() -> {
            ClientControllerCommand command;

            while ((command = (ClientControllerCommand) messenger.readAsCommand()) != null) {
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
