package pieces.move;

import pieces.move.tools.Coordinate;

import java.util.List;

public enum PiecesSteps {
    KING(List.of(new Coordinate(1, 1),
                 new Coordinate(1, 0),
                 new Coordinate(1, -1),
                 new Coordinate(0, 1),
                 new Coordinate(0, -1),
                 new Coordinate(-1, 1),
                 new Coordinate(-1, 0),
                 new Coordinate(-1, -1))),

    KNIGHT(List.of(new Coordinate(2, 1),
            new Coordinate(2, -1),
            new Coordinate(1, 2),
            new Coordinate(1, -2),
            new Coordinate(-2, 1),
            new Coordinate(-2, -1),
            new Coordinate(-1, 2),
            new Coordinate(-1, -2))),


    PAWN(List.of(new Coordinate(0, 1)));


    private final List<Coordinate> steps;

    PiecesSteps(List<Coordinate> steps) {
        this.steps = steps;
    }

    public List<Coordinate> getSteps() {
        return steps;
    }
}
