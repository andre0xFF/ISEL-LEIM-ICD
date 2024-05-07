package pt.isel.icd.patterns.verticals;

import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;

import java.util.ArrayList;

public interface Controller extends Receiver {

    ArrayList<Class<? extends Command<? extends Receiver>>> commandsList();
}
