package models;

import models.ships.components.ShipDamage;
import models.ships.components.ShipPosition;

public interface Ship extends BoardComponent {

    enum ShipName {
        Destroyer,
        Carrier,
        Submarine,
        Battleship
    }

    ShipName getName();

    int getLength();

    ShipDamage getShipDamage();

    ShipPosition getShipPosition();

    default boolean isDestroyed() {
        return getShipDamage().isDestroyed();
    }
}
