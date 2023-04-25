package models;

import java.util.ArrayList;

//public record Player(String name) {
//
//    private static final ArrayList<Token> tokens = new ArrayList<>();
//
//    public Token getToken() {
//        return tokens.get(0);
//    }
//
//    public void addToken(Token token) {
//        tokens.add(token);
//    }
//
//    public ArrayList<Token> getTokens(){
//        return tokens;
//    }
//}

public class Player{

    private ArrayList<Token> tokens;

    private String name;
    public Player(String name){
        this.name = name;

        tokens = new ArrayList<>();
    }

    public Token getToken(){
        return tokens.get(0);
    }


    public void addToken(Token token){
        tokens.add(token);
    }


    public ArrayList<Token> getTokens(){
        return tokens;
    }


}

