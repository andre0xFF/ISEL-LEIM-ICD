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

class BoardShips {
    private final HashMap<ShipName, Integer> registry = new HashMap<>();
    private final HashMap<ShipName, Integer> counter = new HashMap<>();
    private final ArrayList<Ship> ships = new ArrayList<>();

    public void register(ShipName shipName, int totalShips) {
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


    private void initialize() {
        boardShips.register(ShipName.Destroyer, 3);
        boardShips.register(ShipName.Carrier, 1);
        boardShips.register(ShipName.Submarine, 4);
        boardShips.register(ShipName.Battleship, 2);
    }

    public boolean addShip(Ship ship) {
        validateShipPosition(ship);

        // Insert
        return false;
    }

    private boolean validateShipPosition(Ship ship) {
        Point startingPosition = ship.getShipPosition().getStartingPoint();
        Point endingPosition = ship.getShipPosition().getEndingPoint();

        boolean horizontalBoundCheck = 0 < startingPosition.getX() && startingPosition.getX() <= 10
                && 0 <= endingPosition.getX() && endingPosition.getX() <= 10;

        boolean verticalBoundCheck = 0 < startingPosition.getY() && startingPosition.getY() <= 10
                && 0 <= endingPosition.getY() && endingPosition.getY() <= 10;

        System.out.println(horizontalBoundCheck && verticalBoundCheck);
        return horizontalBoundCheck && verticalBoundCheck;
    }

}

class Test {
    public static void main(String[] args) {
        Board board = new Board();

        board.addShip(new Destroyer(new Point(9, 1), Rotation.NORTH));
    }
}
