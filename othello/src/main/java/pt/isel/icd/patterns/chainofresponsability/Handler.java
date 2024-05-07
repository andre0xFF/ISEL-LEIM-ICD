package pt.isel.icd.patterns.chainofresponsability;

public interface Handler<T> {

    void setNext(Handler<T> next);
    void handle(T request);
}
