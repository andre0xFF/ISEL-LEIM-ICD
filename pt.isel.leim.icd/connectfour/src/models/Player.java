package models;

import java.awt.*;
import java.util.ArrayList;

public class Player implements Model {

    private final ArrayList<Token> tokens = new ArrayList<>();
    private final String name;

    public Player(String name) {
        this.name = name;
    }

    /**
     * Removes the first token from the player's tokens list
     * @return the token that was removed
     */
    public Token popToken() {
        return tokens.remove(0);
    }

    /**
     * Adds a token to the player's tokens list
     * @param token the token to add
     */
    public void addToken(Token token) {
        tokens.add(token);
    }

    /**
     * @return the number of tokens the player has
     */
    public int tokensSize() {
        return tokens.size();
    }

    /**
     * @return the color of the player's tokens
     */
    public Color color() {
        return tokens.get(0).color();
    }

    /**
     * @return the name of the player
     */
    public String name() {
        return name;
    }
}
