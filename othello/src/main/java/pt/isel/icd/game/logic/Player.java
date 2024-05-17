package pt.isel.icd.game.logic;

import pt.isel.icd.database.Entity;

public record Player(Piece piece) implements Entity {

    public Piece playPiece() {
        return piece;
    }
}
