package models;

import java.awt.Point;

enum ShipName {
    Destroyer,
    Carrier,
    Submarine,
    Battleship
}

public interface Ship {

    ShipName getName();

    int getLength();

    ShipDamage getShipDamage();

    ShipPosition getShipPosition();

    default boolean isDestroyed() {
        return getShipDamage().isDestroyed();
    }
}

class ShipDamage {
    private final boolean[] damages;

    public ShipDamage(int length) {
        this.damages = new boolean[length];

        initialize();
    }

    private void initialize() {
        for (boolean damage : damages) {
            damage = false;
        }
    }

    public boolean[] getDamages() {
        return damages;
    }

    public boolean setDamage(int index) {
        if (index > damages.length) {
            return false;
        }

        damages[index] = true;

        return damages[index];
    }

    protected boolean isDestroyed() {
        for (boolean damage : getDamages()) {
            if (!damage) {
                return false;
            }
        }

        return true;
    }
}

enum Rotation {
    EAST,
    NORTH,
    WEST,
    SOUTH,
}

class ShipPosition {

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

        return new Point(
                (int) (Math.cos(totalRotation) * (length + startingPoint.getX()) - Math.sin(totalRotation) * startingPoint.getY()),
                (int) (Math.sin(totalRotation) * (length + startingPoint.getX()) + Math.cos(totalRotation) * startingPoint.getY())
        );
    }
}

class Carrier implements Ship {

    private final int length = 5;
    private final static ShipName NAME = ShipName.Carrier;
    private final ShipDamage shipDamage = new ShipDamage(length);
    private final ShipPosition shipPosition;

    public Carrier(Point startPosition, Rotation rotation) {
        this.shipPosition = new ShipPosition(startPosition, rotation, this.length);
    }

    @Override
    public ShipName getName() {
        return NAME;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public ShipDamage getShipDamage() {
        return shipDamage;
    }

    @Override
    public ShipPosition getShipPosition() {
        return shipPosition;
    }
}

class Battleship implements Ship {

    private final int length = 4;
    private final static ShipName NAME = ShipName.Battleship;
    private final ShipDamage shipDamage = new ShipDamage(length);
    private final ShipPosition shipPosition;

    public Battleship(Point startPosition, Rotation rotation) {
        this.shipPosition = new ShipPosition(startPosition, rotation, this.length);
    }

    @Override
    public ShipName getName() {
        return NAME;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public ShipDamage getShipDamage() {
        return shipDamage;
    }

    @Override
    public ShipPosition getShipPosition() {
        return shipPosition;
    }
}

class Destroyer implements Ship {

    private final int length = 3;
    private final static ShipName NAME = ShipName.Destroyer;
    private final ShipDamage shipDamage = new ShipDamage(length);
    private final ShipPosition shipPosition;

    public Destroyer(Point startPosition, Rotation rotation) {
        this.shipPosition = new ShipPosition(startPosition, rotation, this.length);
    }

    @Override
    public ShipName getName() {
        return NAME;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public ShipDamage getShipDamage() {
        return shipDamage;
    }

    @Override
    public ShipPosition getShipPosition() {
        return shipPosition;
    }
}

class Submarine implements Ship {

    private final int length = 2;
    private final static ShipName NAME = ShipName.Submarine;
    private final ShipDamage shipDamage = new ShipDamage(length);
    private final ShipPosition shipPosition;

    public Submarine(Point startPosition, Rotation rotation) {
        this.shipPosition = new ShipPosition(startPosition, rotation, this.length);
    }

    @Override
    public ShipName getName() {
        return NAME;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public ShipDamage getShipDamage() {
        return shipDamage;
    }

    @Override
    public ShipPosition getShipPosition() {
        return shipPosition;
    }
}
