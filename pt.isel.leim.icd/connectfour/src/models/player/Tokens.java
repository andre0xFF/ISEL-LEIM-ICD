package models.player;

import java.awt.*;
import java.util.ArrayList;

/**
 * A collection of tokens
 */
public class Tokens {

    public static final int MAX_TOKENS = 21;

    private ArrayList<Token> tokens = new ArrayList<>();
    private Color color;

    public Tokens(Color color) {
        for (int i = 0; i < MAX_TOKENS; i++) {
            this.tokens.add(new Token(color));
        }

        this.color = color;
    }

    public int size() {
        return this.tokens.size();
    }

    public boolean isEmpty() {
        return this.tokens.isEmpty();
    }

    public boolean add(Token token) {
        return this.tokens.add(token);
    }

    public Token pop() {
        return this.tokens.remove(0);
    }

    public void clear() {
        this.tokens.clear();
    }

    public Color color() {
        return this.color;
    }
}
