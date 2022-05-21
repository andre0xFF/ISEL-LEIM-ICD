package models;

import java.awt.*;
import java.util.HashMap;

public interface Board {

    int horizontalLowerBound = 0;
    int horizontalUpperBound = 10;
    int verticalLowerBound = 0;
    int verticalUpperBound = 10;

    boolean addComponent(Point point, Water water);
    boolean addComponent(Point point, Ship ship, Orientation north);

    static Point calculateShipEndingPoint(Point startingPoint, int length, Orientation orientation) {
        double totalRotation = Math.toRadians(orientation.ordinal() * 90);

        Point endingPoint = new Point((int) startingPoint.getX(), (int) (startingPoint.getY() + length - 1));

        // Translate point back to origin.
        endingPoint.x -= startingPoint.getX();
        endingPoint.y -= startingPoint.getY();

        // Rotated point.
        Point rotatedPoint = new Point(
                (int) Math.round(Math.cos(totalRotation) * (endingPoint.getX()) - Math.sin(totalRotation) * endingPoint.getY()),
                (int) Math.round(Math.sin(totalRotation) * (endingPoint.getX()) + Math.cos(totalRotation) * endingPoint.getY())
        );

        // Translate point back to original position.
        endingPoint.x = (int) (rotatedPoint.getX() + startingPoint.getX());
        endingPoint.y = (int) (rotatedPoint.getY() + startingPoint.getY());

        return endingPoint;
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

    @Override
    public boolean addComponent(Point point, Water water) {
        boardComponents.addComponent(point, water);

        return true;
    }

    private boolean isPointInsideBounds(Point point) {
        return horizontalLowerBound < point.x && point.x <= horizontalUpperBound
                && verticalLowerBound < point.y && point.y <= verticalUpperBound;
    }

    @Override
    public boolean addComponent(Point point, Ship ship, Orientation orientation) {
        // Calculate all ship coordinates.
        Point[] points = new Point[ship.getLength()];

        for (int i = 0; i < points.length; i++) {
            points[i] = Board.calculateShipEndingPoint(point, i, orientation);

            if (!isPointInsideBounds(points[i])) {
                return false;
            }
        }

        // Fill board components' coordinates with ship.
        return boardComponents.addComponent(points, ship);
    }
}

interface BoardComponent {

}

class BoardComponents {

    private final HashMap<Ship.ShipName, Integer> registry = new HashMap<>();
    private final HashMap<Ship.ShipName, Integer> counter = new HashMap<>();
    private final HashMap<Point, BoardComponent> boardComponents = new HashMap<>();

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
            if (boardComponents.containsKey(point)) {
                return false;
            }
        }

        // Are we under the max amount of this ship's type?
        Integer currentAmount = counter.get(ship.getName());
        Integer maxAmount = registry.get(ship.getName());

        if (currentAmount.equals(maxAmount)) {
            return false;
        }

        // Add ship or add partial ship.
        if (!boardComponents.containsValue(ship)) {
            counter.put(ship.getName(), currentAmount + 1);
        }

        for (Point point : points) {
            boardComponents.put(point, ship);
        }

        return true;
    }

    public boolean addComponent(Point point, Ship ship) {
        return addComponent(new Point[]{point}, ship);
    }

    public boolean addComponent(Point point, Water water) {
        if (boardComponents.containsKey(point)) {
            return false;
        }

        boardComponents.put(point, water);

        return true;
    }
}
