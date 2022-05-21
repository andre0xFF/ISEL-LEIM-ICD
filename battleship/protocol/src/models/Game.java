package models;

import models.ships.Destroyer;
import models.ships.components.ShipPosition;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    private Player[] players = new Player[2];

    public Game(Player player1, Player player2) {
        this.players[0] = player1;
        this.players[1] = player2;
    }
}

class BoardShips {
    private final HashMap<Ship.ShipName, Integer> registry = new HashMap<>();
    private final HashMap<Ship.ShipName, Integer> counter = new HashMap<>();
    private final ArrayList<Ship> ships = new ArrayList<>();

    public void register(Ship.ShipName shipName, int totalShips) {
        registry.put(shipName, totalShips);
    }

    public boolean add(Ship ship) {
        Integer count = counter.getOrDefault(ship.getName(), 0);
        Integer limit = registry.get(ship.getName());

        if (limit == null) {
            return false;
        }

        if (count.equals(limit)) {
            return false;
        }

        ships.add(ship);
        counter.put(ship.getName(), count + 1);

        return true;
    }
}

class Board {

    private final BoardShips boardShips = new BoardShips();
    private final int horizontalLowerBound = 0;
    private final int horizontalUpperBound = 10;
    private final int verticalLowerBound = 0;
    private final int verticalUpperBound = 10;


    private void initialize() {
        boardShips.register(Ship.ShipName.Destroyer, 3);
        boardShips.register(Ship.ShipName.Carrier, 1);
        boardShips.register(Ship.ShipName.Submarine, 4);
        boardShips.register(Ship.ShipName.Battleship, 2);
    }

    public boolean addShip(Ship ship) {
        validateShipPosition(ship);

        // Insert
        return false;
    }

    private boolean validateShipPosition(Ship ship) {
        Point startingPosition = ship.getShipPosition().getStartingPoint();
        Point endingPosition = ship.getShipPosition().getEndingPoint();

        boolean horizontalBoundCheck = (
                horizontalLowerBound < startingPosition.getX() && startingPosition.getX() <= horizontalUpperBound
                && horizontalLowerBound < endingPosition.getX() && endingPosition.getX() <= horizontalUpperBound
        );

        boolean verticalBoundCheck = (
                verticalLowerBound < startingPosition.getY() && startingPosition.getY() <= verticalUpperBound
                && verticalLowerBound < endingPosition.getY() && endingPosition.getY() <= verticalUpperBound
        );

        return horizontalBoundCheck && verticalBoundCheck;
    }

}

class Test {
    public static void main(String[] args) {
        Board board = new Board();

        board.addShip(new Destroyer(new Point(9, 1), ShipPosition.Rotation.NORTH));
        board.addShip(new Destroyer(new Point(9, 1), ShipPosition.Rotation.EAST));
        board.addShip(new Destroyer(new Point(9, 1), ShipPosition.Rotation.WEST));
    }
}
