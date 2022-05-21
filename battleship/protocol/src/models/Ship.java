package models;

import models.ships.components.ShipDamage;
import models.ships.components.ShipPosition;

import java.awt.Point;

public interface Ship {

    enum ShipName {
        Destroyer,
        Carrier,
        Submarine,
        Battleship
    }

    interface Component {

    }

    ShipName getName();

    int getLength();

    ShipDamage getShipDamage();

    ShipPosition getShipPosition();

    default boolean isDestroyed() {
        return getShipDamage().isDestroyed();
    }
}
