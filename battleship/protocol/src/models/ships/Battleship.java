package models.ships;

import models.Ship;
import models.ships.components.ShipDamage;
import models.ships.components.ShipPosition;

import java.awt.*;

class Battleship implements Ship {

    private final int length = 4;
    private final static ShipName NAME = Ship.ShipName.Battleship;
    private final ShipDamage shipDamage = new ShipDamage(length);
    private final ShipPosition shipPosition;

    public Battleship(Point startPosition, ShipPosition.Rotation rotation) {
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
