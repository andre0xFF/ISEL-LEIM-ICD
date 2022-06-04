package models.ships;

import models.BoardComponent;
import models.ships.components.ShipDamage;

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

    default boolean isDestroyed() {
        return getShipDamage().isDestroyed();
    }
}
