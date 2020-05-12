package application.protocol.requests;

import java.util.HashMap;

import application.Client;
import application.ServerThread;
import application.protocol.Message;
import application.protocol.Request;
import application.protocol.Response;
import application.protocol.responses.Ok;

public class Echo implements Request {

    @Override
    public String get_request_name() {
        return "echo";
    }

    @Override
    public HashMap<String, String> get_request_params() {
        return null;
    }

    @Override
    public Response execute_request(ServerThread thread) {
        System.out.println("Echo received!");
        thread.send(new Message(new Echo()));
        
        return new Ok();
    }

    @Override
    public Response execute_request(Client client) {
        System.out.println("Echo received!");
        client.send(new Message(new Echo()));
        
        return new Ok();
    }

    @Override
    public void set_request_params(HashMap<String, String> params) { }
}