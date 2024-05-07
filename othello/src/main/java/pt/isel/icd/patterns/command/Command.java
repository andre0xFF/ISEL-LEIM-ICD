package pt.isel.icd.patterns.command;

/**
 * The Command design pattern. The command isn't supposed to perform the work on its own, but rather to pass the call to one of the business logic objects. Concrete commands can receive parameters required to execute the command via the constructor.
 */
public interface Command<T extends Receiver> {

    void setReceiver(T receiver);
    void execute();

    static Command<Receiver> fromXml(String xml) {
        throw new UnsupportedOperationException("fromXml not implemented");
    }

    default String toXml() {
        throw new UnsupportedOperationException("toXml not implemented");
    }
}
