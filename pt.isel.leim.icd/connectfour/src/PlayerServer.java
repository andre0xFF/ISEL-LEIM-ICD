import models.player.GamePlayView;
import models.player.Player;
import models.player.Token;
import models.player.Tokens;
import network.Client;
import network.messages.DropTokenMessage;
import network.messages.LoginMessage;
import network.messages.Message;
import network.messages.PlayTurnMessage;
import network.socket.Listener;
import org.xml.sax.SAXException;

import java.awt.*;
import java.io.IOException;

public class PlayerServer implements Listener<Message>, Player {

    private GamePlayView gamePlayView;
    private final Client client;
    private Tokens tokens = new Tokens();
    private Boolean isLogged = false;
    private String username;

    public PlayerServer(Client client) {
        client.listen(this);

        this.client = client;
    }

    /**
     * Gets the player's username
     *
     * @return the player's username
     */
    @Override
    public String username() {
        return this.username;
    }

    /**
     * Adds a token to the player's tokens list
     *
     * @param token the token to add
     */
    @Override
    public void addToken(Token token) {
        this.tokens.add(token);
    }

    /**
     * Removes the first token from the player's tokens list
     *
     * @return the token that was removed
     */
    @Override
    public Token popToken() {
        return this.tokens.pop();
    }

    /**
     * Gets the number of tokens the player has
     *
     * @return the number of tokens the player has
     */
    @Override
    public int countTokens() {
        return this.tokens.size();
    }

    /**
     * Gets the player's color
     *
     * @return the player's color
     */
    @Override
    public Color color() {
        return this.tokens.color();
    }

    /**
     * Sets the player's tokens list
     *
     * @param tokens the tokens list
     */
    @Override
    public void tokens(Tokens tokens) {
        this.tokens.clear();
        this.tokens = tokens;
    }

    @Override
    public void playTurn() {
        try {
            this.client.write(new PlayTurnMessage());
        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void gamePlayView(GamePlayView gamePlayView) {
        this.gamePlayView = gamePlayView;
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof DropTokenMessage dropTokenMessage) {
            onMessage(dropTokenMessage);
        }
        if (message instanceof LoginMessage loginMessage) {
            onMessage(loginMessage);
        }
    }

    private void onMessage(DropTokenMessage message) {
        // ...
    }

    private void onMessage(LoginMessage message) {
        // TODO: Validate username and password

        this.username = message.username();
        this.isLogged = true;
    }

    public Boolean isLogged() {
        return this.isLogged;
    }
}
