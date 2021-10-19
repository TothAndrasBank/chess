package enemy_step_logic;

import board.BorderValue;
import pieces.move.tools.Coordinate;
import pieces.type.Type;

public class Step {
    private final Coordinate oldCoordinate;
    private final Coordinate newCoordinate;
    private final int movingPieceValue;
    private final int stepLocationValue;

    public Step(final Coordinate oldCoordinate, final Coordinate newCoordinate, final int movingPiece, final int stepLocationValue) {
        this.oldCoordinate = oldCoordinate;
        this.newCoordinate = newCoordinate;
        this.movingPieceValue = movingPiece;
        this.stepLocationValue = stepLocationValue;
    }

    public Coordinate getOldCoordinate() {
        return oldCoordinate;
    }

    public Coordinate getNewCoordinate() {
        return newCoordinate;
    }

    public int getMovingPieceValue() {
        return movingPieceValue;
    }

    public int getStepLocationValue() {
        return stepLocationValue;
    }


    @Override
    public String toString() {

        return String.format("%s moving %c%c==>%c%c to (%s)", Type.getType(movingPieceValue).name(),
                BorderValue.COLUMN.getValue(oldCoordinate.getXPos()),
                BorderValue.ROW.getValue(oldCoordinate.getYPos()),
                BorderValue.COLUMN.getValue(newCoordinate.getXPos()),
                BorderValue.ROW.getValue(newCoordinate.getYPos()),
                Type.getType(stepLocationValue).name());
    }

}
