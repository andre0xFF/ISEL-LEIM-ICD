import application.Server;
import application.models.Function;
import application.models.Resource;
import application.models.User;
import patterns.behavioral.chain.Handler;

public class CheckAuthorization implements Handler<Function<Server>> {

    private User requester;
    private Resource resource;
    private Handler<Function<Server>> next;

    public CheckAuthorization(User requester, Resource resource) {
        this.requester = requester;
        this.resource = resource;
    }

    @Override
    public void setNextHandler(Handler<Function<Server>> handler) {
        this.next = handler;
    }

    @Override
    public void handle(Function<Server> request) {
        // TODO Auto-generated method stub
        
    }
    
}
