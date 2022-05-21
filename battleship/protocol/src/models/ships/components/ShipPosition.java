package models.ships.components;

import models.Ship;

import java.awt.*;

public class ShipPosition implements Ship.Component {

    public enum Rotation {
        EAST,
        NORTH,
        WEST,
        SOUTH,
    }

    private final Point startingPoint;
    private final Rotation rotation;
    private final Integer length;

    public ShipPosition(Point startingPoint, Rotation rotation, Integer length) {
        this.startingPoint = startingPoint;
        this.rotation = rotation;
        this.length = length;
    }

    public Point getStartingPoint() {
        return this.startingPoint;
    }

    public Point getEndingPoint() {
        double totalRotation = Math.toRadians(rotation.ordinal() * 90);

        Point endingPoint = new Point((int) startingPoint.getX(), (int) (startingPoint.getY() + length - 1));

        // Translate point back to origin.
        endingPoint.x -= startingPoint.getX();
        endingPoint.y -= startingPoint.getY();

        // Rotated point.
        Point rotatedPoint = new Point(
                (int) Math.round(Math.cos(totalRotation) * (endingPoint.getX()) - Math.sin(totalRotation) * endingPoint.getY()),
                (int) Math.round(Math.sin(totalRotation) * (endingPoint.getX()) + Math.cos(totalRotation) * endingPoint.getY())
        );

        // Translate point back to original position.
        endingPoint.x = (int) (rotatedPoint.getX() + startingPoint.getX());
        endingPoint.y = (int) (rotatedPoint.getY() + startingPoint.getY());

        return endingPoint;
    }
}
