package pieces;

import pieces.move.tools.Coordinate;
import pieces.move.tools.PiecesPositions;

import javax.swing.ImageIcon;
import java.util.List;

public interface Pieces  {

    int getValue();

    int getReversValue();

    Coordinate getCoordinate();

    void setCoordinate(Coordinate coordinate);

    ImageIcon getImage();

//    List<Coordinate> getMoveZone(final int[][] location);

//    List<Coordinate> getHitZone(final int[][] location);


    List<Coordinate> getMoveZone(PiecesPositions positions);

    List<Coordinate> getHitZone(PiecesPositions positions);

}
