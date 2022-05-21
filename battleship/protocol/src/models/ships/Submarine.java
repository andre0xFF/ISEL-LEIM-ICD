package models.ships;

import models.Ship;
import models.ships.components.ShipDamage;

import java.awt.*;

class Submarine implements Ship {

    private final int length = 2;
    private final static ShipName NAME = ShipName.Submarine;
    private final ShipDamage shipDamage = new ShipDamage(length);

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
}
