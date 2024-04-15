package pt.isel.icd.service.server;

import pt.isel.icd.messaging.Connection;
import pt.isel.icd.messaging.ConnectionSubscriber;
import pt.isel.icd.patterns.modelviewcontroller.Controller;

public interface ServerController extends Controller, ConnectionSubscriber {

    void addUser(User user);

    void removeUser(User user);
}
