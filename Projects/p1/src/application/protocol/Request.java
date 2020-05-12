package application.protocol;

import java.util.HashMap;

import application.Client;
import application.ServerThread;

public interface Request {

	String get_request_name();

	HashMap<String, String> get_request_params();
	void set_request_params(HashMap<String, String> params);

    Response execute_request(ServerThread server);
	Response execute_request(Client client);
}