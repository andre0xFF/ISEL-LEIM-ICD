package models.ships;

import models.Orientation;
import models.Ship;
import models.ships.components.ShipDamage;
import models.ships.components.ShipPosition;

import java.awt.*;

class Submarine implements Ship {

    private final int length = 2;
    private final static ShipName NAME = ShipName.Submarine;
    private final ShipDamage shipDamage = new ShipDamage(length);
    private final ShipPosition shipPosition;

    public Submarine(Point startPosition, Orientation orientation) {
        this.shipPosition = new ShipPosition(startPosition, orientation, this.length);
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
