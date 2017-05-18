package commands;

import models.CommunicationProtocol;

public class Hello implements CommunicationProtocol.Command {

    @Override
    public void execute() {
        this.execute(CommunicationProtocol.Encoding.DEFAULT_ENCODING);
    }

    public void execute(CommunicationProtocol.Encoding encoding) {

    }

    @Override
    public String get_name() {
        return "HELLO";
    }

    @Override
    public CommunicationProtocol.Command get_response() {
        return null;
    }
}
