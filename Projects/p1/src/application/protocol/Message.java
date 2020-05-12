package application.protocol;

import application.Client;
import application.ServerThread;

public class Message {

    Request request;
    Response response;

    public Message(Request request) {
        this(request, null);
    }

    public Message(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

	public void execute(ServerThread server) {
        if (this.response != null) {
            this.response.execute(server);
        }
        else {
            this.response = this.request.execute_request(server);
            server.send(this);
        }
    }

    public void execute(Client client) {
        if (this.response != null) {
            this.response.execute(client);
        }
        else {
            this.response = this.request.execute_request(client);
            client.send(this);
        }
    }

	public Request get_request() {
		return this.request;
	}

	public Response get_response() {
		return this.response;
    }
    
    @Override
    public String toString() {
        String text = String.format("%s", this.request);

        if (this.request != null) {
            text = String.format("%s\n%s", text, this.response);
        }

        return text;
    }
    
}