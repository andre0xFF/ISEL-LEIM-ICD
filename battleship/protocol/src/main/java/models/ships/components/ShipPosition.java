package models.ships.components;

import models.Board;
import models.Orientation;

import java.awt.*;

public class ShipPosition {

    private final Point startingPoint;
    private final Point endingPoint;
    private final Orientation orientation;
    private final Integer length;

    public ShipPosition(Point startingPoint, Orientation orientation, Integer length) {
        this.orientation = orientation;
        this.length = length;
        this.startingPoint = startingPoint;
        this.endingPoint = Board.calculateShipEndingPoint(startingPoint, length, orientation);
    }

    public Point getStartingPoint() {
        return this.startingPoint;
    }

    public Point getEndingPoint() {
        return this.endingPoint;
    }
}
