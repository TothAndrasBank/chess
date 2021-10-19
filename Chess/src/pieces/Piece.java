package pieces;

import board.Images;
import pieces.move.Move;
import pieces.move.tools.Coordinate;
import pieces.move.tools.PiecesPositions;
import pieces.type.Type;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;

public class Piece implements Pieces {
    private final boolean isWhite;
    private Coordinate coordinate;
    private final Type type;
    private final List<Coordinate> hitZone = new ArrayList<>();
    private final List<Coordinate> moveZone = new ArrayList<>();

    /**
     * Type - coordinate(x,y)
     */
    public Piece(final Type type, final Coordinate coordinate) {
        this.type = type;
        isWhite = true;
        this.coordinate = coordinate;
    }

    public Piece(final Type type, final Coordinate coordinate, final boolean isWhite) {
        this.type = type;
        this.isWhite = isWhite;
        this.coordinate = coordinate;

    }

    @Override
    public int getValue() {
        return type.getValue();
    }

    @Override
    public int getReversValue() {
        return type.getValue()*-1;
    }

    @Override
    public ImageIcon getImage() {
        return Images.getType(getValue());
    }

    @Override
    public List<Coordinate> getMoveZone(final PiecesPositions positions) {
        moveZone.clear();
        moveZone.addAll(move(positions, false));
        return moveZone;
    }

    @Override
    public List<Coordinate> getHitZone(final PiecesPositions positions) {
        hitZone.clear();
        hitZone.addAll(move(positions, true));
        return hitZone;
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public void setCoordinate(final Coordinate coordinate) {
        this.coordinate = coordinate;
    }



    private List<Coordinate> move(final PiecesPositions positions, final boolean isHitZone) {
        final Move currentlyPos = new Move(coordinate, isHitZone);
        if (type.getValue() == Type.WHITE_PAWN.getValue()||type.getValue() == Type.BLACK_PAWN.getValue()) {
            return isHitZone ? currentlyPos.getWhitePawnHitZone(positions, false) : currentlyPos.getPawnStep(positions);
        }
        if (type.getValue() == Type.WHITE_KING.getValue()) {
            return currentlyPos.getKingSteps(type.getMove(), positions);
        }
        if (type.getValue() == Type.WHITE_KNIGHT.getValue() ||  type.getValue() == Type.BLACK_KNIGHT.getValue() || type.getValue() == Type.BLACK_KING.getValue()) {//TODO King next to king
            return currentlyPos.getSteps(type.getMove(), positions);
        } else {
            return currentlyPos.getSlide(type.getMove(), positions);
        }

    }

    @Override
    public String toString() {
        return "Piece{" +
                "isWhite=" + isWhite +
                ", coordinate=" + coordinate +
                ", type=" + type +
                ", hitZone=" + hitZone +
                ", moveZone=" + moveZone +
                '}';
    }
}
