package application.protocol;

import application.Client;
import application.ServerThread;

public class Message {

    Request request;

    public Message(Request request) {
        this.request = request;
    }

	public void execute(ServerThread server) {
        if (this.response == null) {
            this.response = this.request.execute_request(server);
            server.send(this);
        }
    }

    public void execute(Client client) {
        if (this.response == null) {
            this.response = this.request.execute_request(client);
            client.send(this.request, this.response);
        }
    }

	public Request get_request() {
		return this.request;
	}

	public Response get_response() {
		return this.response;
    }
    
}