package pieces.move.tools;

public enum Directions {
    UP(new Coordinate(0,-1)),
    RIGHT_UP(new Coordinate(1,-1)),
    LEFT_UP(new Coordinate(-1,-1)),
    DOWN(new Coordinate(0,1)),
    RIGHT_DOWN(new Coordinate(1,1)),
    LEFT_DOWN(new Coordinate(-1,1)),
    LEFT(new Coordinate(-1,0)),
    RIGHT(new Coordinate(1,0));

    private final Coordinate value;

    Directions(final Coordinate value) {
        this.value = value;
    }

    public int getX() {
        return value.getXPos();
    }

    public int getY() {
        return value.getYPos();
    }

    public Coordinate getValue() {
        return value;
    }
}
