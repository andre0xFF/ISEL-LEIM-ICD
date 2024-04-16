package pt.isel.icd.service.server.controllers;

import pt.isel.icd.communication.Connection;
import pt.isel.icd.communication.commands.Command;
import pt.isel.icd.patterns.modelviewcontroller.Model;
import pt.isel.icd.patterns.observer.Subscriber;
import pt.isel.icd.service.server.ServerController;
import pt.isel.icd.service.server.User;
import pt.isel.icd.service.server.commands.ListGames;

import java.util.HashMap;

public class GameListController implements ServerController {

    public GameListController(HashMap<Connection, User> connectionUsers) {
    }

    @Override
    public Class<? extends Command>[] commandTypes() {
        return new Class[] {
                ListGames.class
        };
    }

    @Override
    public void update(Command context) {

    }

    @Override
    public void update(Connection sourceConnection, Command command) {

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
