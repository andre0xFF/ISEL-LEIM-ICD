package application.protocol.requests;

import java.util.HashMap;

import application.Client;
import application.ServerThread;
import application.protocol.Request;
import application.protocol.Response;

public class Logout implements Request {

    @Override
    public String get_request_name() {
        return "disconnect";
    }

    @Override
    public HashMap<String, String> get_request_params() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void set_request_params(HashMap<String, String> params) {
        // TODO Auto-generated method stub

    }

    @Override
    public Response execute_request(ServerThread server) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response execute_request(Client client) {
        // TODO Auto-generated method stub
        return null;
    }
}