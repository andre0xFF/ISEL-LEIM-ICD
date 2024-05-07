package pt.isel.icd.communication;

import pt.isel.icd.patterns.command.Command;
import pt.isel.icd.patterns.command.Receiver;

import java.util.UUID;

public interface ConnectionCommand<T extends Receiver> extends Command<T> {

    void connectionIdentifier(UUID connectionIdentifier);

    default boolean requiresAuthentication() {
        return true;
    }

    static Command<Receiver> fromXml(String xml) {
        throw new UnsupportedOperationException("fromXml not implemented");
    }

    default String toXml() {
        throw new UnsupportedOperationException("toXml not implemented");
    }
}
