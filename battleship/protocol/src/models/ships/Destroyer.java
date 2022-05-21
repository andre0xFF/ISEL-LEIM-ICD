package models.ships;

import models.Ship;
import models.ships.components.ShipDamage;
import models.ships.components.ShipPosition;

import java.awt.*;

public class Destroyer implements Ship {

    private final int length = 3;
    private final static ShipName NAME = ShipName.Destroyer;
    private final ShipDamage shipDamage = new ShipDamage(length);
    private final ShipPosition shipPosition;

    public Destroyer(Point startPosition, ShipPosition.Rotation rotation) {
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
