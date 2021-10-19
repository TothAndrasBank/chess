package pieces.move;

import pieces.move.tools.Coordinate;
import pieces.move.tools.Directions;


import java.util.List;

public enum PiecesSlide {
    QUEEN(List.of(Directions.LEFT_DOWN.getValue(),Directions.RIGHT_DOWN.getValue(),Directions.LEFT_UP.getValue(),Directions.RIGHT_UP.getValue(),Directions.DOWN.getValue(),Directions.UP.getValue(),Directions.LEFT.getValue(),Directions.RIGHT.getValue())),
    ROOK(List.of(Directions.DOWN.getValue(),Directions.UP.getValue(),Directions.LEFT.getValue(),Directions.RIGHT.getValue())),
    BISHOP(List.of(Directions.LEFT_DOWN.getValue(),Directions.RIGHT_DOWN.getValue(),Directions.LEFT_UP.getValue(),Directions.RIGHT_UP.getValue()));

    private final List<Coordinate> slide;

    PiecesSlide(final List<Coordinate> slide) {
        this.slide = slide;
    }

    public List<Coordinate> getSteps() {
        return slide;
    }
}

