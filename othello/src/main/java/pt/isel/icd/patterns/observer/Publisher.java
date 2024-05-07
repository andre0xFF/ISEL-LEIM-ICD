package pt.isel.icd.patterns.observer;

/**
 * The Observer design pattern. The Publisher issues events of interest to other objects. These events occur when the publisher changes its state or executes some behaviors. Publishers contain a subscription infrastructure that lets new subscribers join and current subscribers leave the list.
 */
public interface Publisher<T> {

    void subscribe(Class<? extends T> eventType, Subscriber<T> subscriber);

    void unsubscribe(Class<? extends T> eventType, Subscriber<T> subscriber);

    void publish();
}
