import models.player.GamePlayView;
import models.player.Player;
import models.player.Token;
import models.player.Tokens;
import network.Client;
import network.messages.DropTokenMessage;
import network.messages.LogInMessage;
import network.messages.Message;
import network.messages.PlayTurnMessage;
import network.socket.Listener;
import org.xml.sax.SAXException;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

/**
 * Represents a player that is connected to the server
 */
public class RemotePlayer implements Listener<Message>, Player {

    private GamePlayView gamePlayView;
    private final Client client;
    private Tokens tokens;
    private Boolean isLogged = false;
    private String username;

    public RemotePlayer(Client client) {
        client.listen(this);

        this.client = client;
        this.tokens = new Tokens(
                new Color(
                        new Random().nextInt(256),
                        new Random().nextInt(256),
                        new Random().nextInt(256)
                )
        );
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
     * Sends a message to the client to drop a token
     */
    @Override
    public void playTurn() {
        try {
            this.client.write(new PlayTurnMessage());
        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }
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
    public void gamePlayView(GamePlayView gamePlayView) {
        this.gamePlayView = gamePlayView;
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof DropTokenMessage dropTokenMessage) {
            onMessage(dropTokenMessage);
        } else if (message instanceof LogInMessage loginMessage) {
            onMessage(loginMessage);
        }
    }

    private void onMessage(DropTokenMessage message) {
        this.gamePlayView.dropToken(message.column());
    }

    private void onMessage(LogInMessage message) {
        // TODO: Validate username and password

        this.username = message.username();
        this.isLogged = true;
    }

    public Boolean isLogged() {
        return this.isLogged;
    }

    public boolean isConnected() {
        return this.client.isConnected();
    }
}
