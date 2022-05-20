package controllers;

public interface Controller<T> {

    String getName();
    void onMessage(T message);
}
