package models.ships;

import models.Ship;
import models.ships.components.ShipDamage;

public class Destroyer implements Ship {

    private final int length = 3;
    private final static ShipName NAME = ShipName.Destroyer;
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
