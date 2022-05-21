package models.ships;

import models.Ship;
import models.ships.components.ShipDamage;

class Carrier implements Ship {

    private final int length = 5;
    private final static ShipName NAME = ShipName.Carrier;
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
