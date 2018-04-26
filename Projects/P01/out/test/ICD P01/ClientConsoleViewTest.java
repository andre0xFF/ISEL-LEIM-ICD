package tests;

import client.Client;
import client.ClientController;
import client.views.console.ConsoleView;

class ClientConsoleViewTest {

    @org.junit.jupiter.api.Test
    void set_controller() {

        Client client = new Client();
        ConsoleView console = new ConsoleView(client);

        ClientController controller = new ClientController(client);
        console.set(controller);

        assert "rewr" == "fsdfsdf";
    }
}