import models.Model;
import models.Users;
import models.player.GamePlayView;
import models.player.Player;
import models.player.Token;
import models.player.TokensStack;
import network.Client;
import network.messages.*;
import network.socket.Listener;
import org.xml.sax.SAXException;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

/**
 * Represents a player on the server side
 */
public class RemotePlayer implements Listener<Message>, Player {

    private GamePlayView gamePlayView;
    private final Client client;
    private TokensStack tokensStack;
    private Boolean authenticated = false;
    private String username;
    private final Model.Database database = new Model.Database();

    public RemotePlayer(Client client) {
        client.listen(this);

        this.client = client;
        this.tokensStack = new TokensStack(
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
        this.tokensStack.add(token);
    }

    /**
     * Removes the first token from the player's tokens list
     *
     * @return the token that was removed
     */
    @Override
    public Token popToken() {
        return this.tokensStack.pop();
    }

    /**
     * Gets the number of tokens the player has
     *
     * @return the number of tokens the player has
     */
    @Override
    public int countTokens() {
        return this.tokensStack.size();
    }

    /**
     * Gets the player's color
     *
     * @return the player's color
     */
    @Override
    public Color color() {
        return this.tokensStack.color();
    }

    /**
     * Sets the player's tokens list
     *
     * @param tokensStack the tokens list
     */
    @Override
    public void tokens(TokensStack tokensStack) {
        this.tokensStack.clear();
        this.tokensStack = tokensStack;
    }

    @Override
    public void gamePlayView(GamePlayView gamePlayView) {
        this.gamePlayView = gamePlayView;
    }

    public Boolean authenticated() {
        return this.authenticated;
    }

    public boolean isConnected() {
        return this.client.isConnected();
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof DropTokenMessage dropTokenMessage) {
            onMessage(dropTokenMessage);
        } else if (message instanceof AskLoginMessage askLoginMessage) {
            onMessage(askLoginMessage);
        }
    }

    private void onMessage(DropTokenMessage message) {
        this.gamePlayView.dropToken(message.column());
    }

    private void onMessage(AskLoginMessage askLoginMessage) {
        boolean authenticated = login(askLoginMessage.username(), askLoginMessage.password());

        try {
            this.client.write(new GiveLoginResultMessage(authenticated));
        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean login(String username, char[] password) {
        Users users;

        try {
            users = (Users) this.database.load(Users.class);
        } catch (IOException e) {
            return false;
        }

        this.authenticated = users.authenticate(username, password);

        if (!this.authenticated) {
            return false;
        }

        this.username = username;

        return true;
    }

    /**
     * Called when it's the player's turn
     */
    @Override
    public void onPlayTurn() {
        try {
            this.client.write(new OnPlayTurnMessage());
        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Called when it's the other player's turn
     */
    @Override
    public void onWaitTurn() {
        try {
            this.client.write(new OnWaitTurnMessage());
        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Called when the player won the game
     */
    @Override
    public void onWin() {
        try {
            this.client.write(new OnWinMessage());
        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Called when the player lost the game
     */
    @Override
    public void onLoss() {
        try {
            this.client.write(new OnLossMessage());
        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Called when the player dropped a token
     *
     * @param column the column where the token was dropped
     * @param row    the row where the token was dropped
     */
    @Override
    public void onTokenDropped(int column, int row, Color color) {
        try {
            this.client.write(new OnTokenDroppedMessage(column, row, color));
        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Called when the player dropped a token in a full column
     *
     * @param column the column where the player tried to drop the token
     */
    @Override
    public void onTokenNotDropped(int column) {
        try {
            this.client.write(new OnTokenNotDroppedMessage(column));
        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
