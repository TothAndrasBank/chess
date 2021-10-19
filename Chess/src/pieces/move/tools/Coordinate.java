package pieces.move.tools;

import java.util.Objects;

public record Coordinate(int xPos, int yPos) {

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Coordinate that = (Coordinate) obj;
        return xPos == that.xPos && yPos == that.yPos;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xPos, yPos);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "xPos=" + xPos +
                ", yPos=" + yPos +
                '}';
    }
}
