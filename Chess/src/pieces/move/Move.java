package pieces.move;

import board.Board;
import pieces.Piece;
import pieces.Pieces;
import pieces.move.tools.Coordinate;
import pieces.move.tools.PiecesPositions;
import pieces.type.Type;

import java.util.ArrayList;
import java.util.List;

public class Move {
    private final int OUTSIDE_THE_BOARD = -2;
    private final int ENEMY_PIECE = -1;
    private final int NOTHING = 0;
    private final int PLAYER_PIECE = 1;
    private final int ONE_STEP_LEFT = -1;
    private final int ONE_STEP_RIGHT = 1;
    private final int ONE_STEP_UP = -1;


    private final Coordinate currentlyStay;
    private final int[] range = {1, 2, 3, 4, 5, 6, 7};
    private final boolean isHitZone;



    public Move(final Coordinate currentlyStay, final boolean isHitZone) {
        this.currentlyStay = currentlyStay;
        this.isHitZone = isHitZone;
    }

    public Move() {
        currentlyStay = null;
        isHitZone = true;
    }

    public List<Coordinate> getSteps(final List<Coordinate> stepsDirections, final PiecesPositions positions) {
        final List<Coordinate> move = new ArrayList<>(8);
        for (final Coordinate stepsDirection : stepsDirections) {
            final int stepX = currentlyStay.getXPos() + stepsDirection.getXPos();
            final int stepY = currentlyStay.getYPos() + stepsDirection.getYPos();
            switch (isValidMove(stepX, stepY, positions)) {
                case NOTHING, ENEMY_PIECE -> move.add(new Coordinate(stepX, stepY));
                case PLAYER_PIECE -> {
                    if (isHitZone) {
                        move.add(new Coordinate(stepX, stepY));
                    }
                }
            }
        }
        return move;
    }
    public List<Coordinate> getKingSteps(final List<Coordinate> stepsDirections, final PiecesPositions positions) {
        final List<Coordinate> move = new ArrayList<>();
        for (final Coordinate stepsDirection : stepsDirections) {
            final int stepX = currentlyStay.getXPos() + stepsDirection.getXPos();
            final int stepY = currentlyStay.getYPos() + stepsDirection.getYPos();
            switch (isValidKingMove(stepX, stepY, positions)) {
                case NOTHING, ENEMY_PIECE -> move.add(new Coordinate(stepX, stepY));
                case PLAYER_PIECE -> {
                    if (isHitZone) {
                        move.add(new Coordinate(stepX, stepY));
                    }
                }
            }
        }
        return move;
    }


    public List<Coordinate> getSlide(final List<Coordinate> slideDirections, final PiecesPositions positions) {
        final List<Coordinate> move = new ArrayList<>(8);
        for (final Coordinate stepsDirection : slideDirections) {
            distanceLoop:
            for (final int distance : range) {
                final int stepX = currentlyStay.getXPos() + distance * stepsDirection.getXPos();
                final int stepY = currentlyStay.getYPos() + distance * stepsDirection.getYPos();
                switch (isValidMove(stepX, stepY, positions)) {
                    case NOTHING:
                        move.add(new Coordinate(stepX, stepY));
                        break;
                    case ENEMY_PIECE:
                        move.add(new Coordinate(stepX, stepY));
                        break distanceLoop;
                    case PLAYER_PIECE:
                        if (isHitZone) {
                            move.add(new Coordinate(stepX, stepY));
                            break distanceLoop;
                        }
                    case OUTSIDE_THE_BOARD:
                        break distanceLoop;
                }
            }
        }
        return move;
    }

    public List<Coordinate> getPawnStep(final PiecesPositions positions) {
        final List<Coordinate> move = new ArrayList<>(8);
        final int x = currentlyStay.getXPos();
        final int y = currentlyStay.getYPos() + ONE_STEP_UP;
        //Step
        if (isValidMove(x, y, positions) == NOTHING) {
            move.add(new Coordinate(x, y));
            if (y == 5 && isValidMove(x, y + ONE_STEP_UP, positions) == NOTHING) {
                move.add(new Coordinate(x, y + ONE_STEP_UP));
            }
        }
        //Hit
        if (isValidMove(x + ONE_STEP_LEFT, y, positions) == ENEMY_PIECE) {
            move.add(new Coordinate(x + ONE_STEP_LEFT, y));
        }
        if (isValidMove(x + ONE_STEP_RIGHT, y, positions) == ENEMY_PIECE) {
            move.add(new Coordinate(x + ONE_STEP_RIGHT, y));
        }
        return move;
    }

    public List<Coordinate> getWhitePawnHitZone(final PiecesPositions positions, final boolean isWhite) {
        final List<Coordinate> move = new ArrayList<>(8);
        final int x = currentlyStay.getXPos();
        final int y;
        if (isWhite) {
            y = currentlyStay.getYPos() + ONE_STEP_UP;
        } else {
            final int ONE_STEP_DOWN = 1;
            y = currentlyStay.getYPos() + ONE_STEP_DOWN;
        }
        final int leftHit = isValidMove(x + ONE_STEP_LEFT, y, positions);
        final int rightHit = isValidMove(x + ONE_STEP_RIGHT, y, positions);
        if (leftHit != OUTSIDE_THE_BOARD) {
            move.add(new Coordinate(x + ONE_STEP_LEFT, y));
        }
        if (rightHit != OUTSIDE_THE_BOARD) {
            move.add(new Coordinate(x + ONE_STEP_RIGHT, y));
        }
        return move;
    }

    public boolean[][] getEnemyHitZone(final PiecesPositions positions) {
        final List<Pieces> enemyPieces = new ArrayList<>(16);
        for (int xPos = 0; xPos < positions.size(); xPos++) {
            for (int yPos = 0; yPos < positions.size(); yPos++) {
                if (positions.getLocationValue(xPos,yPos) < 0) {
                    enemyPieces.add(new Piece(Type.getType(positions.getLocationValue(xPos,yPos)), new Coordinate(xPos, yPos) ));
                }
            }
        }
        final boolean[][] enemyHitZone = new boolean[Board.SIZE][Board.SIZE];
        for (final Pieces enemyPiece : enemyPieces) {
            for (final Coordinate step : enemyPiece.getHitZone(positions)) {
                enemyHitZone[step.getXPos()][step.getYPos()] = true;
            }
        }
        return enemyHitZone;
    }


    public int isValidKingMove(final int x, final int y, final PiecesPositions positions) {
        final boolean[][] enemyHitZone = getEnemyHitZone(positions);
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            if (enemyHitZone[x][y]) {
                return -3;
            }
            return Integer.compare(positions.getLocationValue(x,y), 0);
        }
        return OUTSIDE_THE_BOARD;
    }


    private int isValidMove(final int x, final int y, final PiecesPositions positions) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            return Integer.compare(positions.getLocationValue(x,y), 0);
        }
        return OUTSIDE_THE_BOARD;
    }
}
