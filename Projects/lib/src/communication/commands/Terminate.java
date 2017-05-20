package communication.commands;

import client.Client;
import communication.Protocol.Command;
import communication.Protocol.Encoder;

public class Terminate implements Command {

    @Override
    public void execute(Client client) {

    }

    @Override
    public String encode(Encoder encoder) {
        return null;
    }

    @Override
    public Command decode(String message) {
        return null;
    }
}
