package communication.commands;

import client.Client;
import communication.Protocol;

public class OpenAccount implements Protocol.Command {

    @Override
    public void execute(Client client) {

    }

    @Override
    public String encode(Protocol.Encoder encoder) {
        return null;
    }

    @Override
    public Protocol.Command decode(String message) {
        return null;
    }

    @Override
    public String get_name() {
        return null;
    }

    @Override
    public Protocol.Command get_reponse() {
        return null;
    }
}
