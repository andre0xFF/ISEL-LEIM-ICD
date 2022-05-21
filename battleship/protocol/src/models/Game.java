package models;

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

interface BoardComponent {

}

interface Board {

    int horizontalLowerBound = 0;
    int horizontalUpperBound = 10;
    int verticalLowerBound = 0;
    int verticalUpperBound = 10;

    BoardComponents getBoardComponents();

    boolean addWater(Point point);
}

class EnemyBoard implements Board {
    private final BoardComponents boardComponents = new BoardComponents();

    @Override
    public BoardComponents getBoardComponents() {
        return boardComponents;
    }

    @Override
    public boolean addWater(Point point) {
        return boardComponents.addComponent(point, new Water());
    }
}

class PlayerBoard implements Board {

    private final BoardComponents boardComponents = new BoardComponents();

    private void initialize() {
        boardComponents.register(Ship.ShipName.Destroyer, 3);
        boardComponents.register(Ship.ShipName.Carrier, 1);
        boardComponents.register(Ship.ShipName.Submarine, 4);
        boardComponents.register(Ship.ShipName.Battleship, 2);
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

    @Override
    public BoardComponents getBoardComponents() {
        return boardComponents;
    }
}

class BoardComponents {
    private final HashMap<Ship.ShipName, Integer> registry = new HashMap<>();
    private final HashMap<Ship.ShipName, Integer> counter = new HashMap<>();

    private final HashMap<Point, BoardComponent> boardComponents = new HashMap<>();

    public void register(Ship.ShipName shipName, int totalShips) {
        registry.put(shipName, totalShips);
    }

    public boolean addComponent(Point point, Ship ship) {
        if (boardComponents.containsKey(point)) {
            return false;
        }

        Integer count = counter.getOrDefault(ship.getName(), 0);
        Integer limit = registry.get(ship.getName());

        if (limit == null) {
            return false;
        }

        if (count.equals(limit)) {
            return false;
        }

        boardComponents.put(point, ship);
        counter.put(ship.getName(), count + 1);

        return true;
    }

    public boolean addComponent(Point point, Water water) {
        if (boardComponents.containsKey(point)) {
            return false;
        }

        boardComponents.put(point, water);

        return true;
    }
}
