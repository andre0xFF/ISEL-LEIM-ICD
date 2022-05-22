package models_bkp.ships;

import models_bkp.BoardComponent;
import models_bkp.ships.components.ShipDamage;

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
