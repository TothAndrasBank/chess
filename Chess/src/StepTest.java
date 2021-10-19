import org.junit.Assert;
import org.junit.Test;
import pieces.Piece;
import pieces.move.tools.Coordinate;
import pieces.move.tools.PiecesPositions;
import pieces.type.Type;

import java.util.Collections;

public class StepTest extends Assert {
    int currentlyX = 0;
    int currentlyY = 0;
    Piece queen = new Piece(Type.WHITE_QUEEN, new Coordinate(1, 1));

    Piece king = new Piece(Type.BLACK_KING, new Coordinate(5, 5) );

//    EnemyStep enemyStep = new EnemyStep(List.of(queen), List.of(king));
    int[][] location = {{0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}};

//    int[][] location = {{0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 0}};

    @Test
    public void testy() {

        location[queen.getCoordinate().getXPos()][queen.getCoordinate().getYPos()] = queen.getValue();
        location[king.getCoordinate().getXPos()][king.getCoordinate().getYPos()] = king.getValue();

        final PiecesPositions positions = new PiecesPositions(location);
        final PiecesPositions positions1 = new PiecesPositions(Collections.singletonList(queen), Collections.singletonList(king));
        assertEquals(positions, positions1);

    }


    @Test
    public void stepTest() {

    }
}
