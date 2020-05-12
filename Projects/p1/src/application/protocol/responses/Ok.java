package application.protocol.responses;

import application.Client;
import application.ServerThread;
import application.protocol.Response;

public class Ok implements Response {

    @Override
    public String Name() {
        return "ok";
    }

    @Override
    public void execute(ServerThread server) { }

    @Override
    public void execute(Client client) { }
    
}