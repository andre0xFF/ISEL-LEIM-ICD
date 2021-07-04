package functions;

import application.Server;
import application.models.Function;

public class TestFunction1 implements Function<Server> {

    private Server receiver;

    @Override
    public void execute() {
        // todo: Write on Server database
        // todo: send result
        
    }

    @Override
    public void setReceiver(Server receiver) {
        this.receiver = receiver;
    }

}
