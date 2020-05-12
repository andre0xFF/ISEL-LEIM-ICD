package application.protocol.responses;

import application.Client;
import application.ServerThread;
import application.protocol.Response;

public class Denied implements Response {

    @Override
    public String Name() {
        return "denied";
    }

    @Override
    public void execute(ServerThread server) { }

    @Override
    public void execute(Client client) { }
    
}