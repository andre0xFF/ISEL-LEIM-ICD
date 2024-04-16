package pt.isel.icd.service.server.controllers;

import pt.isel.icd.communication.Connection;
import pt.isel.icd.communication.commands.Command;
import pt.isel.icd.patterns.modelviewcontroller.Model;
import pt.isel.icd.patterns.observer.Subscriber;
import pt.isel.icd.service.server.ServerController;
import pt.isel.icd.service.server.User;
import pt.isel.icd.service.server.commands.*;

import java.util.HashMap;

public class GameController implements ServerController {

    private GameListController gameListController;
    private HashMap<Connection, User> connectionUserMap = new HashMap<>();

    public GameController(HashMap<Connection, User> connectionUsers) {
        this(connectionUsers, null);
    }

    public GameController(HashMap<Connection, User> connectionUsers, GameListController existingGameListController) {
        gameListController = existingGameListController;
    }

    @Override
    public Class<? extends Command>[] commandTypes() {
        return new Class[]{
                CreateGameFailure.class,
                CreateGameSuccess.class,
                GameState.class,
                JoinGameFailure.class,
                JoinGameSuccess.class,
                LeaveGameFailure.class,
                LeaveGameSuccess.class,
                MakeMoveFailure.class,
                MakeMoveSuccess.class,
        };
    }

    @Override
    public void update(Command context) {

    }

    @Override
    public void update(Connection sourceConnection, Command command) {
        User user = connectionUserMap.get(sourceConnection);
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
