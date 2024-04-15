package pt.isel.icd.service.server.controllers;

import pt.isel.icd.communication.commands.Command;
import pt.isel.icd.patterns.modelviewcontroller.Model;
import pt.isel.icd.patterns.observer.Subscriber;
import pt.isel.icd.service.server.ServerController;
import pt.isel.icd.service.server.User;

public class GameController implements ServerController {

    @Override
    public void addUser(User user) {

    }

    @Override
    public void removeUser(User user) {

    }

    @Override
    public Class<? extends Command>[] commandTypes() {
        return new Class[0];
    }

    @Override
    public void update(Command context) {

    }


    @Override
    public <T extends Model> void model(T model) {

    }

    @Override
    public void subscribe(Subscriber<Command> subscriber) {

    }

    @Override
    public void unsubscribe(Subscriber<Command> subscriber) {

    }

    @Override
    public void publish() {

    }
}
