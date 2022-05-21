package models.ships;

import models.Ship;
import models.ships.components.ShipDamage;

class Battleship implements Ship {

    private final int length = 4;
    private final static ShipName NAME = Ship.ShipName.Battleship;
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
