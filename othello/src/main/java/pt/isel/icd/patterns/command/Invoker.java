package pt.isel.icd.patterns.command;

/**
 * Invoker is part of the Command design pattern. Concrete invokers are responsible for initiating command requests.
 */
public interface Invoker<T extends Receiver> {

    void setCommand(Command<T> command);
    void executeCommand();
}
