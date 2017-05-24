package communication.encoders;

import communication.Communication.Command;
import communication.Communication.Encoder;

import java.util.ArrayList;

public class JSON implements Encoder {

    private ArrayList<Command> commands;

    @Override
    public String encode(Command command) {
        return command.encode(this);
    }

    @Override
    public Command decode(String message) {
        return Encoder.decode(this.commands, message);
    }

    @Override
    public void add(Command command) {
        this.commands.add(command);
    }

    @Override
    public void set(ArrayList<Command> commands) {
        this.commands = commands;
    }

    @Override
    public ArrayList<Command> get_commands() {
        return this.commands;
    }
}
