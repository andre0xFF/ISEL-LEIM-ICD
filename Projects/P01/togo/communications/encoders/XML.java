package communications.encoders;

import models.CommunicationProtocol;
import models.CommunicationProtocol.Command;
import models.CommunicationProtocol.Encoder;

public class XML implements Encoder {

    @Override
    public String get_name() {
        return "XML";
    }

    @Override
    public String encode(Command cmd) {
        return cmd.encode(this);
    }

    public Command decode(String msg, Command[] cmds) {
        return null;
    }

    @Override
    public Command decode(String msg) {
        return Encoder.decode(this.cmds, msg);
    }
}
