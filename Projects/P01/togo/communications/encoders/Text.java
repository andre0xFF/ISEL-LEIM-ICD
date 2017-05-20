package communications.encoders;

import models.CommunicationProtocol.Encoder;
import models.CommunicationProtocol.Command;

public class Text implements Encoder {

    @Override
    public String get_name() {
        return "Text";
    }

    @Override
    public String encode(Command cmd) {
        return Encoder.encode(this, cmd);
    }

    @Override
    public Command decode(String msg, Command[] cmds) {
        return Encoder.decode(this, cmds, msg);
    }
}
