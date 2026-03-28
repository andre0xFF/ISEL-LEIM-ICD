package pt.isel.icd.communication;

/**
 * Command pattern interface. Commands encapsulate a request
 * and delegate execution to a receiver.
 */
public interface Command<T> {
    void setReceiver(T receiver);
    void execute();
}
