import models.player.GamePlayView;
import models.player.Player;
import models.player.Token;
import models.player.Tokens;
import network.Client;
import network.messages.GiveLogInAcceptedMessage;
import network.messages.AskLogInMessage;
import network.messages.Message;
import network.socket.Listener;

import java.awt.*;

public class LocalPlayer implements Listener<Message>, Player {

    private GamePlayView gamePlayView;

    public LocalPlayer(Client client) {
        client.listen(this);
    }

    @Override
    public String username() {
        return null;
    }

    @Override
    public void addToken(Token token) {

    }

    @Override
    public Token popToken() {
        return null;
    }

    @Override
    public int countTokens() {
        return 0;
    }

    @Override
    public Color color() {
        return null;
    }

    @Override
    public void tokens(Tokens tokens) {

    }

    @Override
    public void playTurn() {

    }

    @Override
    public void gamePlayView(GamePlayView gamePlayView) {
        this.gamePlayView = gamePlayView;

    }

    @Override
    public void onMessage(Message message) {

        if(message instanceof GiveLogInAcceptedMessage logInMessage){
            onMessage(logInMessage);
        }

    }

    private void onMessage(GiveLogInAcceptedMessage message){

    }
}
