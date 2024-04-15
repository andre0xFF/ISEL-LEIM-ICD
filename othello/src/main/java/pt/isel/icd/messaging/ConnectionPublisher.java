package pt.isel.icd.messaging;

import pt.isel.icd.messaging.messages.Message;
import pt.isel.icd.patterns.observer.Publisher;

public interface ConnectionPublisher extends Publisher<Message> {

    void subscribe(ConnectionSubscriber subscriber);

    void unsubscribe(ConnectionSubscriber subscriber);

    int connectionSubscribersTotal();
}
