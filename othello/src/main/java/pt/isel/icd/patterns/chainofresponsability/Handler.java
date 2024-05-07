package pt.isel.icd.patterns.chainofresponsability;

import pt.isel.icd.patterns.command.Receiver;

public interface Handler<T> {

    default void setNext(Handler<T> next) {}
    void handle(T request);
}
