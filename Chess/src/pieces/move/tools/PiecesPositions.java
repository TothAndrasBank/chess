package pieces.move.tools;

import board.Board;
import enemy_step_logic.Step;
import pieces.Pieces;

import java.util.Arrays;
import java.util.List;

public class PiecesPositions {
    private int[][] location = new int[Board.SIZE][Board.SIZE];

    public PiecesPositions(final PiecesPositions positions) {
        this.location = Arrays.copyOf(positions.location, location.length);
    }

    public PiecesPositions(final int[][] location) {
        this.location = Arrays.copyOf(location, location.length);
    }

    public PiecesPositions(final List<Pieces> playerPieces, final List<Pieces> enemyPieces) {
        for (final Pieces playerPiece : playerPieces) {
            location[playerPiece.getCoordinate().getXPos()][playerPiece.getCoordinate().getYPos()] = playerPiece.getValue();
        }
        for (final Pieces enemyPiece : enemyPieces) {
            location[enemyPiece.getCoordinate().getXPos()][enemyPiece.getCoordinate().getYPos()] = enemyPiece.getValue();
        }
    }

    public int size() {
        return Board.SIZE;
    }

    public int getLocationValue(final int x, final int y) {
        return location[x][y];
    }

    public int getLocationValue(final Coordinate coordinate) {
        return location[coordinate.getXPos()][coordinate.getYPos()];
    }


    public void setLocationValue(final int x, final int y, final int value) {
        location[x][y] = value;
    }

    public void setLocationValue(final Coordinate coordinate, final int value) {
        location[coordinate.getXPos()][coordinate.getYPos()] = value;
    }
    public void setLocationValue(final Step step) {
        location[step.getNewCoordinate().getXPos()][step.getNewCoordinate().getYPos()] = step.getMovingPieceValue();
        location[step.getOldCoordinate().getXPos()][step.getOldCoordinate().getYPos()] = 0;

    }


    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PiecesPositions positions = (PiecesPositions) obj;

        return Arrays.deepEquals(location, positions.location);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(location);
    }

    @Override
    public String toString() {
        return "PiecesPositions{" +
                "location=" + Arrays.toString(location) +
                '}';
    }
}
