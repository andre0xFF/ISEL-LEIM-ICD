package models;

import java.util.ArrayList;

public record Player(String name) {

    private static final ArrayList<Token> tokens = new ArrayList<>();

    public Token getToken() {
        return tokens.get(0);
    }

    public void addToken(Token token) {
        tokens.add(token);
    }
}
