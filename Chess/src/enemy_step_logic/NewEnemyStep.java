package enemy_step_logic;


import pieces.Pieces;
import pieces.move.Move;
import pieces.move.tools.Coordinate;
import pieces.Piece;
import pieces.move.tools.PiecesPositions;
import pieces.type.Type;
import pieces.type.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewEnemyStep {
    final static int BOARD_SIZE = 8;
    boolean[][] playerHitZone = new boolean[BOARD_SIZE][BOARD_SIZE];
    List<Pieces> playerPieces = new ArrayList<>(16);
    List<Pieces> enemyPieces = new ArrayList<>(16);

    public NewEnemyStep(final List<Pieces> playerPieces, final List<Pieces> enemyPieces) {
        this.playerPieces.addAll(playerPieces);
        this.enemyPieces.addAll(enemyPieces);
    }


    public Step nextStep(final PiecesPositions positions,  final List<Pieces> playerPieces, final List<Pieces> enemyPieces) {

        System.out.println("-------------------START--------------------------");
        refreshPieces( playerPieces, enemyPieces);
        playerHitZone = calculatePlayerHitZones(positions, playerPieces);
        Step nextStep = canMakeCheckMate(positions);
        if (Objects.nonNull(nextStep)) {
            return nextStep;
        }

        if (isCheck()) {
            System.out.println("CHECK!");
            nextStep = eliminateTheCheck(positions, true);
            System.out.println("eliminateTheCheck(location, true)");
            if (Objects.nonNull(nextStep)) {
                return nextStep;
            }
            nextStep = stepOutInCheck(positions);
            System.out.println("nextStep = stepOutInCheck(location)");
            if (Objects.nonNull(nextStep)) {
                return nextStep;
            }
            nextStep = eliminateTheCheck(positions, false);
            System.out.println("eliminateTheCheck(location, false)");
            if (Objects.nonNull(nextStep)) {
                return nextStep;
            }
            System.out.println("Check Mate!!");
        } else {
            nextStep = defensiveHit(positions, Type.WHITE_QUEEN.getValue());
            System.out.println("defensiveHit(location, Type.WHITE_QUEEN.getValue())");
            if (Objects.nonNull(nextStep)) {
                return nextStep;
            }
            System.out.println("hitByValue(location, Type.WHITE_QUEEN.getValue()");
            nextStep = hitByValue(positions, Type.WHITE_QUEEN.getValue());
            if (Objects.nonNull(nextStep)) {
                return nextStep;
            }
            System.out.println("defensiveHit(location, Type.WHITE_ROOK.getValue()");
            nextStep = defensiveHit(positions, Type.WHITE_ROOK.getValue());
            if (Objects.nonNull(nextStep)) {
                return nextStep;
            }
            System.out.println("defensiveHit(location, Type.WHITE_KNIGHT.getValue()");
            nextStep = defensiveHit(positions, Type.WHITE_KNIGHT.getValue());
            if (Objects.nonNull(nextStep)) {
                return nextStep;
            }
            System.out.println("defensiveHit(location, Type.WHITE_BISHOP.getValue()");
            nextStep = defensiveHit(positions, Type.WHITE_BISHOP.getValue());
            if (Objects.nonNull(nextStep)) {
                return nextStep;
            }
            System.out.println("hitByValue(location, Type.WHITE_ROOK.getValue()");
            nextStep = hitByValue(positions, Type.WHITE_ROOK.getValue());
            if (Objects.nonNull(nextStep)) {
                return nextStep;
            }
            System.out.println("hitByValue(location, Type.WHITE_KNIGHT.getValue()");
            nextStep = hitByValue(positions, Type.WHITE_KNIGHT.getValue());
            if (Objects.nonNull(nextStep)) {
                return nextStep;
            }
            System.out.println("hitByValue(location, Type.WHITE_BISHOP.getValue()");
            nextStep = hitByValue(positions, Type.WHITE_BISHOP.getValue());
            if (Objects.nonNull(nextStep)) {
                return nextStep;
            }
            System.out.println("defensiveHit(location, Type.WHITE_PAWN.getValue()");
            nextStep = defensiveHit(positions, Type.WHITE_PAWN.getValue());
            if (Objects.nonNull(nextStep)) {
                return nextStep;
            }
            System.out.println();
        }


        inDangerousZone(Type.BLACK_QUEEN.getValue());

        System.out.println("-------------------END--------------------------");
        return null;
    }


    private boolean[][] calculatePlayerHitZones(final PiecesPositions positions, final List<Pieces> playerPieces) {
        final boolean[][] playerHitZone = new boolean[BOARD_SIZE][BOARD_SIZE];
        for (final Pieces piece : playerPieces) {
            for (final Coordinate coordinate : piece.getHitZone(positions)) {
                playerHitZone[coordinate.getXPos()][coordinate.getYPos()] = true;
            }
        }
        return playerHitZone;
    }

    public void refreshPieces( final List<Pieces> playerPieces, final List<Pieces> enemyPieces) {
        this.playerPieces.clear();
        this.playerPieces.addAll(playerPieces);
        this.enemyPieces.clear();
        this.enemyPieces.addAll(enemyPieces);
    }

    public Step canMakeCheckMate(final PiecesPositions positions) {
        final PiecesPositions newPositions = new PiecesPositions(positions);

        for (final Pieces enemyPiece : enemyPieces) {
            for (final Coordinate step : enemyPiece.getHitZone(positions)) {
                newPositions.setLocationValue(step, enemyPiece.getValue());
                newPositions.setLocationValue(enemyPiece.getCoordinate(), 0);
                final Step newStep = new Step(enemyPiece.getCoordinate(), step, enemyPiece.getValue(), newPositions.getLocationValue(step));
                if (isPlayerKingCheckMate(newPositions)) {
                    if (canPlayerHitThisPiece(newPositions, newStep)) {
                        return canStepThatTheKinkIsNotInCheck(newPositions, newStep) ? newStep : null;
                    }
                }
                newPositions.setLocationValue(step, positions.getLocationValue(step));
                newPositions.setLocationValue(enemyPiece.getCoordinate(), positions.getLocationValue(enemyPiece.getCoordinate()));
            }
        }
        return null;
    }

    public boolean canPlayerHitThisPiece(final PiecesPositions positions, final Step step) {
        final boolean[][] playerHitZone = calculatePlayerHitZones(positions, playerPieces);
        return !playerHitZone[step.getNewCoordinate().getXPos()][step.getNewCoordinate().getYPos()];
    }

    public boolean isPlayerKingCheckMate(final PiecesPositions positions) {
        final Pieces whitKing = getPiecesByValue(Type.WHITE_KING.getValue());
        final Move move = new Move();
        final boolean[][] enemyHitZone = move.getEnemyHitZone(positions);
        final int whiteKingXPos = whitKing.getCoordinate().getXPos();
        final int whiteKingYPos = whitKing.getCoordinate().getYPos();

        return whitKing.getMoveZone(positions).size() == 0 && enemyHitZone[whiteKingXPos][whiteKingYPos];
    }

    public Step eliminateTheCheck(final PiecesPositions positions, final boolean isSafe) {
        Step newStep;
        for (final Pieces enemyPiece : enemyPieces) {
            for (final Coordinate step : enemyPiece.getHitZone(positions)) {
                if (enemyPiece.getValue() == Type.BLACK_KING.getValue()) {
                    continue;
                }
                final int x = step.getXPos();
                final int y = step.getYPos();
                if (isSafe && playerHitZone[x][y]) {
                    continue;
                }
                if (positions.getLocationValue(x, y) >= 0) {
                    newStep = new Step(enemyPiece.getCoordinate(), step, enemyPiece.getValue(), positions.getLocationValue(x, y));
                    if (canStepThatTheKinkIsNotInCheck(positions, newStep)) {
                        return newStep;
                    }
                }
            }
        }
        return null;
    }

    public Step stepOutInCheck(final PiecesPositions positions) {
        Step newStep;
        final List<Step> stepList = new ArrayList<>();
        final Pieces blackKing = getPiecesByValue(Type.BLACK_KING.getValue());
        for (final Coordinate step : blackKing.getHitZone(positions)) {
            newStep = new Step(blackKing.getCoordinate(), step, blackKing.getValue(), positions.getLocationValue(step));
            if (positions.getLocationValue(step) >= 0 && canStepThatTheKinkIsNotInCheck(positions, newStep)) {
                stepList.add(newStep);
            }
        }
        Step maxValueHitStep = stepList.size() > 0 ? stepList.get(0) : null;
        for (final Step step : stepList) {
            if (step.getStepLocationValue() > maxValueHitStep.getStepLocationValue()) {
                maxValueHitStep = step;
            }
        }
        return maxValueHitStep;
    }

    public Step hitByValue(final PiecesPositions positions, final int value) {
        final Step step;
        for (final Pieces enemyPiece : enemyPieces) {
            if (enemyPiece.getReversValue() >= value) {
                continue;
            }
            for (final Coordinate coordinate : enemyPiece.getHitZone(positions)) {
                if (positions.getLocationValue(coordinate) == value) {
                    step = new Step(enemyPiece.getCoordinate(), coordinate, enemyPiece.getValue(), value);
                    return canStepThatTheKinkIsNotInCheck(positions, step) ? step : null;
                }
            }
        }
        return null;
    }


    public Step defensiveHit(final PiecesPositions positions, final int value) {
        final Step step;
        for (final Pieces enemyPiece : enemyPieces) {
            for (final Coordinate coordinate : enemyPiece.getHitZone(positions)) {
                final int x = coordinate.getXPos();
                final int y = coordinate.getYPos();
                final PiecesPositions newPositions = new PiecesPositions(positions);
                newPositions.setLocationValue(coordinate, enemyPiece.getValue());
                newPositions.setLocationValue(enemyPiece.getCoordinate(), 0);
                final boolean[][] playerHitZone = calculatePlayerHitZones(newPositions, playerPieces);
                if (positions.getLocationValue(coordinate) == value && !playerHitZone[x][y]) {
                    step = new Step(enemyPiece.getCoordinate(), coordinate, enemyPiece.getValue(), value);
                    return canStepThatTheKinkIsNotInCheck(positions, step) ? step : null;
                }
            }
        }

        return null;
    }

    public boolean inDangerousZone(final int value) {
        boolean isDangerous = false;
        for (final Pieces enemyPiece : enemyPieces) {
            if (enemyPiece.getValue() == value) {
                isDangerous = playerHitZone[enemyPiece.getCoordinate().getXPos()][enemyPiece.getCoordinate().getYPos()];
            }
            if (isDangerous) {
                return true;
            }
        }

        return false;
    }

    public boolean isCheck() {
        final Pieces blackKing = getPiecesByValue(Type.BLACK_KING.getValue());

        return playerHitZone[blackKing.getCoordinate().getXPos()][blackKing.getCoordinate().getYPos()];
    }


    public boolean canStepThatTheKinkIsNotInCheck(final PiecesPositions positions, final Step newStep) {
        if (Objects.isNull(newStep)) {
            System.out.print("Step is null    ---");
            return false;
        }
        final List<Type> types = new ArrayList<>(Types.WHITE.getTypes());
        final PiecesPositions newPositions = new PiecesPositions(positions);
        newPositions.setLocationValue(newStep);
        final Pieces king = getPiecesByValue(Type.BLACK_KING.getValue());
        if (newStep.getMovingPieceValue() == Type.BLACK_KING.getValue()) {
            king.setCoordinate(newStep.getNewCoordinate());
        }
        for (final Type type : types) {
            if (canHitTheGivenPiece(newPositions, king.getCoordinate(), type)) {
                return false;
            }
        }
        return true;
    }


    public boolean canHitTheGivenPiece(final PiecesPositions positions, final Coordinate givenPieceCoordinate, final Type hitterPiece) {
        final Pieces pieces = new Piece(hitterPiece, givenPieceCoordinate);
        for (final Coordinate step : pieces.getHitZone(positions)) {
            if (positions.getLocationValue(step) == hitterPiece.getValue()) {
                return true;
            }
        }
        return false;
    }


    public Pieces getPiecesByValue(final int value) {
        if (value > 0) {
            for (final Pieces playerPiece : playerPieces) {
                if (playerPiece.getValue() == value) {
                    return new Piece(Type.getType(value), playerPiece.getCoordinate());
                }
            }
        } else {
            for (final Pieces enemyPiece : enemyPieces) {
                if (enemyPiece.getValue() == value) {
                    return new Piece(Type.getType(value), enemyPiece.getCoordinate());
                }
            }
        }

        return null;
    }
}