package models;

import java.awt.*;
import java.util.ArrayList;

public class Player {

    private final ArrayList<Token> tokens = new ArrayList<>();
    private final String name;

    public Player(String name) {
        this.name = name;
    }

    public Token popToken() {
        return tokens.remove(0);
    }

    public void addToken(Token token) {
        tokens.add(token);
    }

    public int getTokensCount() {
        return tokens.size();
    }

    public Color color() {
        return tokens.get(0).color();
    }
}
