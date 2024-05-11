package pt.isel.icd.patterns.observer;

/**
 * Subscriber is part of the Observer design pattern. Concrete subscribers perform actions in response to notifications issues by the publisher.
 */
public interface Subscriber<T> {
    
    void update(T context);
}
