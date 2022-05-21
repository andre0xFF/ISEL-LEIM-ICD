import controllers.AccountController;
import sessions.Client;
import views.AccountView;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        Client client = new Client();

        AccountController accountController = new AccountController(client);
        client.setController(accountController);
        AccountView accountView = new AccountView(accountController);
        accountController.setListener(accountView);

        accountView.authenticate();

        LobbyController lobbyController = new LobbyController(client);
        client.setController(lobbyController);
        LobbyView lobbyView = new LobbyView(lobbyController);
        lobbyController.setListener(lobbyView);

        PreBattleController preBattleController = new PreBattleController(client);
        client.setController(preBattleController);
        PreBattleView preBattleView = new PreBattleView(preBattleController);
        preBattleController.setListener(preBattleView);

        BattleController battleController = new BattleController(client);
        client.setController(battleController);
        BattleView battleView = new BattleView(battleController);
        battleController.setListener(battleView);
    }
}
