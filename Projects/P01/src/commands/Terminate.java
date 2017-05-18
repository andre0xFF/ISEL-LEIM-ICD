package commands;

import models.CommunicationProtocol;
import server.BankInternalClient;

public class Terminate implements CommunicationProtocol.Command {

    @Override
    public void execute() { }

    public void execute(BankInternalClient client) {
        client.set_active(false);
    }

    @Override
    public String get_name() {
        return "Terminate";
    }

    @Override
    public CommunicationProtocol.Command get_response() {
        return null;
    }
}
