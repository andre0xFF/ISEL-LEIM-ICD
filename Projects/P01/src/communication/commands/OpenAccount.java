package communication.commands;

import communication.Communication;
import server.Client;
import server.Server.Worker;

public class OpenAccount implements Communication.Command {

    @Override
    public void execute(Worker client) {

    }

    @Override
    public void execute(Client client) {

    }

    @Override
    public String encode(Communication.Encoder encoder) {
        return null;
    }

    @Override
    public Communication.Command decode(String message) {
        return null;
    }

    @Override
    public String get_name() {
        return null;
    }

    @Override
    public Communication.Command get_response() {
        return null;
    }
}
