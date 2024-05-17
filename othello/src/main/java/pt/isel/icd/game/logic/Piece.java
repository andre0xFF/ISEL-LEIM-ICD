package pt.isel.icd.game.logic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import pt.isel.icd.database.Entity;

public enum Piece implements Entity {
    X, O, EMPTY;

    @JsonValue
    public String toValue() {
        return this.name();
    }

    @JsonCreator
    public static Piece forValue(String value) {
        return Piece.valueOf(value.toUpperCase());
    }
}
