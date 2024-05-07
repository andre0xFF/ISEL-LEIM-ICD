package pt.isel.icd.communication;

import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;

import java.util.UUID;

public class DisconnectCommand implements Command<Receiver> {
    @Override
    public void setReceiver(Receiver receiver) {

    }

    @Override
    public void execute() {
        System.out.println("Disconnected!");
    }
}
