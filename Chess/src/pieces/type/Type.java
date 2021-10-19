package pieces.type;

import pieces.move.tools.Coordinate;
import pieces.move.PiecesSlide;
import pieces.move.PiecesSteps;

import java.util.List;

public enum Type {
    WHITE_KING(10, PiecesSteps.KING.getSteps()),
    WHITE_QUEEN(9, PiecesSlide.QUEEN.getSteps()),
    WHITE_ROOK(5, PiecesSlide.ROOK.getSteps()),
    WHITE_KNIGHT(3, PiecesSteps.KNIGHT.getSteps()),
    WHITE_BISHOP(2, PiecesSlide.BISHOP.getSteps()),
    WHITE_PAWN(1, PiecesSteps.PAWN.getSteps()),
    BLACK_KING(-10, PiecesSteps.KING.getSteps()),
    BLACK_QUEEN(-9, PiecesSlide.QUEEN.getSteps()),
    BLACK_ROOK(-5, PiecesSlide.ROOK.getSteps()),
    BLACK_KNIGHT(-3, PiecesSteps.KNIGHT.getSteps()),
    BLACK_BISHOP(-2, PiecesSlide.BISHOP.getSteps()),
    BLACK_PAWN(-1, PiecesSteps.PAWN.getSteps()),
    EMPTY_SPOT(0, null);

    private final int value;
    private final List<Coordinate> move;

    Type(final int value, final List<Coordinate> step) {
        this.move = step;
        this.value = value;
    }



    public int getValue() {
        return value;
    }

    public static Type getType(final int index) {
        return switch (index) {
            case 10 -> WHITE_KING;
            case 9 -> WHITE_QUEEN;
            case 5 -> WHITE_ROOK;
            case 3 -> WHITE_KNIGHT;
            case 2 -> WHITE_BISHOP;
            case 1 -> WHITE_PAWN;
            case -10 -> BLACK_KING;
            case -9 -> BLACK_QUEEN;
            case -5 -> BLACK_ROOK;
            case -3 -> BLACK_KNIGHT;
            case -2 -> BLACK_BISHOP;
            case -1 -> BLACK_PAWN;
            default -> EMPTY_SPOT;
        };
    }

    public List<Coordinate> getMove() {
        return move;
    }


}
