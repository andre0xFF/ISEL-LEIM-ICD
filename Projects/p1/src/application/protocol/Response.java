package application.protocol;

import application.Client;
import application.ServerThread;

public interface Response {

	String Name();

    void execute(ServerThread server);
	void execute(Client client);
}
