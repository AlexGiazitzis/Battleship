package api.game;

/**
 * @author : AlexGiazitzis
 * @project : Battleship
 * @created : 02-Feb-21 at 2:53 PM
 **/

enum Ship {

    AIRCRAFT_CARRIER(5, "Aircraft Carrier"),
    BATTLESHIP(4, "Battleship"),
    SUBMARINE(3, "Submarine"),
    CRUISER(3, "Cruiser"),
    DESTROYER(2, "Destroyer");

    private final int size;
    private final String name;
    protected static final int maxShips = 5;

    Ship(int size, String name) {

        this.size = size;
        this.name = name;

    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }


}

public class Battleship {
    private final Ship ship;
    private boolean alive;
    private int xStart;
    private int xFinish;
    private int yStart;
    private int yFinish;
    private int health;

    public Battleship(Ship ship) {
        this.ship = ship;
        this.alive = true;
        this.xStart = 0;
        this.xFinish = 0;
        this.health = this.ship.getSize();
    }

    public Ship getShip() {
        return ship;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getxStart() {
        return xStart;
    }

    public void setxStart(int xStart) {
        this.xStart = xStart;
    }

    public int getxFinish() {
        return xFinish;
    }

    public void setxFinish(int xFinish) {
        this.xFinish = xFinish;
    }

    public int getyStart() {
        return yStart;
    }

    public void setyStart(int yStart) {
        this.yStart = yStart;
    }

    public int getyFinish() {
        return yFinish;
    }

    public void setyFinish(int yFinish) {
        this.yFinish = yFinish;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

}
