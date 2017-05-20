package communications.encoders;

import models.CommunicationProtocol.Command;
import models.CommunicationProtocol.Encoder;

public class JSON implements Encoder {

    @Override
    public String get_name() {
        return null;
    }

    @Override
    public String encode(Command cmd) {
        return Encoder.encode(this, cmd);
    }

    public Command decode(String msg, Command[] cmds) {
        return Encoder.decode(this, cmds, msg);
    }
}
