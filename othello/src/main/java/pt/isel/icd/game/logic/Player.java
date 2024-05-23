package pt.isel.icd.game.logic;

import pt.isel.icd.database.Entity;

public class Player implements Entity {

    private final Piece piece;
    private int score;

    public Player(Piece piece) {
        this.piece = piece;
    }

    public Piece playPiece() {
        return piece;
    }

    public void resetScore() {
        score = 0;
    }

    public int score() {
        return score;
    }

    public void incrementScore() {
        score++;
    }
}
