package tests;

import server.Server;
import server.ServerController;
import server.views.console.ConsoleView;

public class ServerConsoleViewTest {

    @org.junit.jupiter.api.Test
    void set_controller() {

        Server server = new Server();
        ConsoleView console = new ConsoleView(server);

        ServerController controller = new ServerController(server);
        console.set(controller);
    }
}
