package application.protocol.formats;

import java.util.ArrayList;
import java.util.HashMap;

import application.protocol.Format;
import application.protocol.Message;
import application.protocol.Request;
import application.protocol.Response;
import application.protocol.requests.Echo;

public class XML implements Format {

    ArrayList<Request> requests = new ArrayList<>();
    ArrayList<Response> responses = new ArrayList<>();

    @Override
    public String encode(Message message) {
        Request request = message.get_request();
        Response response = message.get_response();

        return String.format(
            "<message>%s%s</message>", encode(request), encode(response)
        );
    }

    private String encode(Request request) {
        return String.format(
            "<request name=\"%s\">%s</request>", request.get_request_name(),  encode(request.get_request_params())
        );
    }

    private String encode(Response response) {
        if (response == null) {
            return "";
        }

        return String.format(
            "<response name=\"%s\"/>", response.Name()
        );
    }

    private String encode(HashMap<String, String> params) {
        if (params == null) {
            return "";
        }
        
        String text = "";

        for (String param : params.keySet()) {
            text = String.format(
                "%s<parameter name=\"%s\">%s</parameter>", text, param, params.get(param)
            );
        }

        return text;
    }

    @Override
    public Message decode(String text) {
        // TODO
        return new Message(new Echo());
    }

    @Override
    public void register(Request request) {
        this.requests.add(request);
    }

    @Override
    public void register(Response response) {
        this.responses.add(response);
    }
    
}