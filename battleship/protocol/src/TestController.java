import controllers.ClientController;
import controllers.ServerController;
import controllers.commands.Authenticate;
import controllers.commands.PingCommand;
import models.Account;
import sessions.Server;

public class TestController {
    public static void main(String[] args) {
        Server server = new Server();

        ServerController serverController = new ServerController();

        serverController.setServer(server);
        serverController.acceptCommunication();

        ClientController clientController = new ClientController();
        clientController.acceptCommunication();
        clientController.sendCommand(new PingCommand());
        clientController.sendCommand(new Authenticate(new Account("andre", "12345")));
    }
}
