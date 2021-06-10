package application.protocol.requests;

import java.util.HashMap;

import application.Client;
import application.ServerThread;
import application.protocol.Request;

public class Ping implements Request {

    @Override
    public String get_request_name() {
        return "ping";
    }

    @Override
    public HashMap<String, String> get_request_params() {
        return null;
    }

    @Override
    public void set_request_params(HashMap<String, String> params) { }

    @Override
    public void execute_request(ServerThread server) {
        // TODO Auto-generated method stub

    }

    @Override
    public void execute_request(Client client) {
        // TODO Auto-generated method stub

    }

    
}