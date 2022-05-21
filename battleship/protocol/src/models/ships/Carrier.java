package models.ships;

import models.Ship;
import models.ships.components.ShipDamage;
import models.ships.components.ShipPosition;

import java.awt.*;

class Carrier implements Ship {

    private final int length = 5;
    private final static ShipName NAME = ShipName.Carrier;
    private final ShipDamage shipDamage = new ShipDamage(length);
    private final ShipPosition shipPosition;

    public Carrier(Point startPosition, ShipPosition.Rotation rotation) {
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
