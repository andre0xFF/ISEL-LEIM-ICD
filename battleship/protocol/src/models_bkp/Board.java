package models_bkp;

import models_bkp.ships.Ship;

import java.awt.*;
import java.util.HashMap;


class BoardModel {
    private final HashMap<Point, Ship> ships = new HashMap<>();

    public boolean addShip(Point point, Ship ship) {
        // Is the ship inside board?
        // Is ship type registered?
        // Is ship type total under limit?
        // Are board coordinates empty?

        return true;
    }

    public boolean startBattle() {
        // Are all ships in the board?

        return true;
    }
}

public interface Board {

    int horizontalLowerBound = 0;
    int horizontalUpperBound = 10;
    int verticalLowerBound = 0;
    int verticalUpperBound = 10;

    boolean addComponent(Point point, Water water);

    boolean addComponent(Point point, Ship ship, Orientation north);

    static Point calculateShipEndingPoint(Point startingPoint, int length, Orientation orientation) {
        double totalRotation = Math.toRadians(orientation.ordinal() * 90);

        Point endingPoint = new Point((int) startingPoint.getX() + length, (int) (startingPoint.getY()));

        // Translate point back to origin.
        endingPoint.x -= startingPoint.getX();
        endingPoint.y -= startingPoint.getY();

        // Rotated point.
        Point rotatedPoint = new Point(
                (int) Math.round(Math.cos(totalRotation) * endingPoint.getX() - Math.sin(totalRotation) * endingPoint.getY()),
                (int) Math.round(Math.sin(totalRotation) * endingPoint.getX() + Math.cos(totalRotation) * endingPoint.getY())
        );

        // Translate point back to original position.
        endingPoint.x = (int) (rotatedPoint.getX() + startingPoint.getX());
        endingPoint.y = (int) (rotatedPoint.getY() + startingPoint.getY());

        return endingPoint;
    }
}

class PlayerBoard implements Board {

    private final BoardComponents boardComponents = new BoardComponents();

    public PlayerBoard() {
        initialize();
    }

    private void initialize() {
        boardComponents.register(Ship.ShipName.Destroyer, 3);
        boardComponents.register(Ship.ShipName.Carrier, 1);
        boardComponents.register(Ship.ShipName.Submarine, 4);
        boardComponents.register(Ship.ShipName.Battleship, 2);
    }

    @Override
    public boolean addComponent(Point point, Water water) {
        if (isShipInsideBoard(point)) {
            return false;
        }

        return boardComponents.addComponent(point, water);
    }

    private boolean isShipInsideBoard(Point point) {
        return !(horizontalLowerBound < point.getX()) || !(point.getX() <= horizontalUpperBound)
                || !(verticalLowerBound < point.getY()) || !(point.getY() <= verticalUpperBound);
    }

    @Override
    public boolean addComponent(Point point, Ship ship, Orientation orientation) {
        // Calculate all ship coordinates.
        Point[] points = new Point[ship.getLength()];

        for (int i = 0; i < points.length; i++) {
            points[i] = Board.calculateShipEndingPoint(point, i, orientation);

            if (isShipInsideBoard(points[i])) {
                return false;
            }
        }

        // Fill board components' coordinates with ship.
        return boardComponents.addComponent(points, ship);
    }
}

class BoardComponents {

    private final HashMap<Ship.ShipName, Integer> registry = new HashMap<>();
    private final HashMap<Ship.ShipName, Integer> counter = new HashMap<>();
    private final HashMap<Point, BoardComponent> components = new HashMap<>();

    public void register(Ship.ShipName shipName, int totalShips) {
        registry.put(shipName, totalShips);
    }

    public boolean addComponent(Point[] points, Ship ship) {
        // Is the ship registered?
        if (!registry.containsKey(ship.getName())) {
            return false;
        }

        // Are the points empty?
        for (Point point : points) {
            if (components.containsKey(point)) {
                return false;
            }
        }

        // Are we under the max amount of this ship's type?
        Integer currentAmount = counter.getOrDefault(ship.getName(), 0);
        Integer maxAmount = registry.get(ship.getName());

        if (currentAmount.equals(maxAmount)) {
            return false;
        }

        // Add ship or add partial ship.
        if (!components.containsValue(ship)) {
            counter.put(ship.getName(), currentAmount + 1);
        }

        for (Point point : points) {
            components.put(point, ship);
        }

        return true;
    }

    public boolean addComponent(Point point, Water water) {
        if (components.containsKey(point)) {
            return false;
        }

        components.put(point, water);

        return true;
    }
}
