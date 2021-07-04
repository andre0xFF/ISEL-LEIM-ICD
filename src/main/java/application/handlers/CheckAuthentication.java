package application.handlers;

import application.Server;
import application.models.Function;
import patterns.behavioral.chain.Handler;

public class CheckAuthentication implements Handler<Function<Server>> {

    @Override
    public void setNextHandler(Handler<Function<Server>> handler) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void handle(Function<Server> request) {
        // TODO Auto-generated method stub
        
    }
    
}
