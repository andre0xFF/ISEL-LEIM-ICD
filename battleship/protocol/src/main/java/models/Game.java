package models;

public class Game {

    private Player[] players = new Player[2];

    public Game(Player player1, Player player2) {
        this.players[0] = player1;
        this.players[1] = player2;
    }
}

