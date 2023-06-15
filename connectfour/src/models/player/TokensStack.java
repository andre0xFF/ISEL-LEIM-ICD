package models.player;

import java.awt.*;
import java.util.ArrayList;

/**
 * A collection of tokens
 */
public class TokensStack {

    public static final int MAX_TOKENS = 21;

    private ArrayList<Token> tokens = new ArrayList<>();
    private Color color;

    public TokensStack(Color color) {
        for (int i = 0; i < MAX_TOKENS; i++) {
            this.tokens.add(new Token(color));
        }

        this.color = color;
    }

    /**
     * Returns the number of tokens in the stack
     *
     * @return the number of tokens in the stack
     */
    public int size() {
        return this.tokens.size();
    }

    /**
     * Returns true if the stack is empty
     *
     * @return true if the stack is empty
     */
    public boolean isEmpty() {
        return this.tokens.isEmpty();
    }

    /**
     * Adds a token to the stack
     *
     * @param token the token to add
     * @return true if the token was added
     */
    public boolean add(Token token) {
        return this.tokens.add(token);
    }

    /**
     * Removes a token from the stack
     *
     * @return the removed token
     */
    public Token pop() {
        return this.tokens.remove(0);
    }

    /**
     * Clears all tokens from the stack
     */
    public void clear() {
        this.tokens.clear();
    }

    /**
     * Returns the color of the stack
     *
     * @return the color of the stack
     */
    public Color color() {
        return this.color;
    }
}
