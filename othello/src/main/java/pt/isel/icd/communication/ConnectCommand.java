package pt.isel.icd.communication;

import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;

public class ConnectCommand implements Command<Receiver> {
    @Override
    public void setReceiver(Receiver receiver) {

    }

    @Override
    public void execute() {
        System.out.println("Connected!");
    }
}
