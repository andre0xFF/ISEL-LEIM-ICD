package pt.isel.icd.communication;

import pt.isel.icd.communication.commands.Command;
import pt.isel.icd.patterns.observer.Publisher;

public interface ConnectionPublisher extends Publisher<Command> {

    void subscribe(ConnectionSubscriber subscriber);

    void unsubscribe(ConnectionSubscriber subscriber);

    int connectionSubscribersTotal();
}
