import board.Board;
import board.Images;
import enemy_step_logic.NewEnemyStep;
import enemy_step_logic.Step;
import pieces.Piece;
import pieces.Pieces;
import pieces.move.tools.Coordinate;
import pieces.move.tools.PiecesPositions;
import pieces.type.Type;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Game implements ActionListener {
    final static int BOARD_SIZE = 8;
    private final Board board = new Board();
    private final List<Pieces> playerPieces = new ArrayList<>(16);
    private final List<Pieces> enemyPieces = new ArrayList<>(16);
    private final PiecesPositions positions;
    boolean isActive = false;
    boolean[][] canSteps = new boolean[BOARD_SIZE][BOARD_SIZE];
    private int activePieceIndex;
    NewEnemyStep enemyStep;

    public Game() {
        pieceInit();
        positions = new PiecesPositions(enemyPieces, playerPieces);
        start();
        enemyStep = new NewEnemyStep(playerPieces, enemyPieces);
    }


    private void activate(final int x, final int y) {
        if (positions.getLocationValue(x, y) > 0) {
            for (int i = 0; i < playerPieces.size(); i++) {
                if (playerPieces.get(i).getCoordinate().getXPos() == x && playerPieces.get(i).getCoordinate().getYPos() == y) {
                    greenPaintingWheretoGo(playerPieces.get(i).getMoveZone(positions));
                    activePieceIndex = i;
                    isActive = true;
                    return;
                }
            }
        } else if (positions.getLocationValue(x, y) == 0) {
            for (final Pieces enemyPiece : enemyPieces) {
                board.getBoard(enemyPiece.getCoordinate().getXPos(),
                               enemyPiece.getCoordinate().getYPos()).setBackground(Color.RED);
            }
            for (final Pieces playerPiece : playerPieces) {
                board.getBoard(playerPiece.getCoordinate().getXPos(),
                               playerPiece.getCoordinate().getYPos()).setBackground(Color.BLUE);
            }
        }
    }

    private void step(final int x, final int y) {
        if (canSteps[x][y]) {
            final Step playerStep = new Step(playerPieces.get(activePieceIndex).getCoordinate(),
                                             new Coordinate(x, y),
                                             playerPieces.get(activePieceIndex).getValue(),
                                             positions.getLocationValue(playerPieces.get(activePieceIndex).getCoordinate()));
            newStep(playerStep);
            final Step enemyNextStep = enemyStep.nextStep(positions,  playerPieces, enemyPieces);
            if (Objects.nonNull(enemyNextStep)) {
                newStep(enemyNextStep);
            } else {
                System.out.println("Enemy Not have new step");
            }
        }
        board.setBoardDefaultColor();
        isActive = false;
    }

    private void newStep(final Step step) {
        final int oldXPos = step.getOldCoordinate().getXPos();
        final int oldYPos = step.getOldCoordinate().getYPos();
        final int newXPos = step.getNewCoordinate().getXPos();
        final int newYPos = step.getNewCoordinate().getYPos();
        final int oldPositionsValue = positions.getLocationValue(step.getOldCoordinate());
        if (oldPositionsValue > 0) {
            setPieceLocation(playerPieces, step.getNewCoordinate(), step.getOldCoordinate());
        } else if (oldPositionsValue < 0) {
            setPieceLocation(enemyPieces, step.getNewCoordinate(), step.getOldCoordinate());
        }
        final int newPositionsValue = positions.getLocationValue(step.getNewCoordinate());
        if (newPositionsValue > 0) {
            removePieces(playerPieces, step.getNewCoordinate());
        } else if (newPositionsValue < 0) {
            removePieces(enemyPieces, step.getNewCoordinate());
        }
        positions.setLocationValue(step);
        board.setBoardImage(oldXPos, oldYPos, null);
        board.setBoardImage(newXPos, newYPos, Images.getType(step.getMovingPieceValue()));
    }

    private void setPieceLocation(final List<Pieces> pieces,
                                  final Coordinate newCoordinate,
                                  final Coordinate oldCoordinate) {
        for (final Pieces piece : pieces) {
            if (piece.getCoordinate().equals(oldCoordinate)) {
                piece.setCoordinate(newCoordinate);
            }
        }
    }

    private void removePieces(final List<Pieces> pieces, final Coordinate newCoordinate) {
        for (final Pieces piece : pieces) {
            if (piece.getCoordinate().equals(newCoordinate)) {
                pieces.remove(piece);
                break;
            }
        }
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if (e.getSource() == board.getBoard(x, y)) {
                    if (isActive) {
                        step(x, y);
                    } else {
                        activate(x, y);
                    }

                }
            }
        }
    }

    private void start() {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                board.getBoard(x, y).addActionListener(this);
                final int currentlyPieces = positions.getLocationValue(x, y);
                if (currentlyPieces != 0) {
                    board.setBoardImage(x, y, Images.getType(currentlyPieces));
                }
            }
        }
    }

    private void pieceInit() {
        playerPieces.add(new Piece(Type.WHITE_ROOK, new Coordinate(0, 7), true));
        playerPieces.add(new Piece(Type.WHITE_KNIGHT, new Coordinate(1, 7), true));
        playerPieces.add(new Piece(Type.WHITE_BISHOP, new Coordinate(2, 7), true));
        playerPieces.add(new Piece(Type.WHITE_QUEEN, new Coordinate(3, 7), true));
        playerPieces.add(new Piece(Type.WHITE_KING, new Coordinate(4, 7), true));
        playerPieces.add(new Piece(Type.WHITE_BISHOP, new Coordinate(5, 7), true));
        playerPieces.add(new Piece(Type.WHITE_KNIGHT, new Coordinate(6, 7), true));
        playerPieces.add(new Piece(Type.WHITE_ROOK, new Coordinate(7, 7), true));

        enemyPieces.add(new Piece(Type.BLACK_ROOK, new Coordinate(0, 0), false));
        enemyPieces.add(new Piece(Type.BLACK_KNIGHT, new Coordinate(1, 0), false));
        enemyPieces.add(new Piece(Type.BLACK_BISHOP, new Coordinate(2, 0), false));
        enemyPieces.add(new Piece(Type.BLACK_QUEEN, new Coordinate(3, 0), false));
        enemyPieces.add(new Piece(Type.BLACK_KING, new Coordinate(4, 0), false));
        enemyPieces.add(new Piece(Type.BLACK_BISHOP, new Coordinate(5, 0), false));
        enemyPieces.add(new Piece(Type.BLACK_KNIGHT, new Coordinate(6, 0), false));
        enemyPieces.add(new Piece(Type.BLACK_ROOK, new Coordinate(7, 0), false));


        for (int i = 0; i < BOARD_SIZE; i++) {
//            enemyPieces.add(new Piece(Type.BLACK_PAWN, new Coordinate(i, 1), false));
            playerPieces.add(new Piece(Type.WHITE_PAWN, new Coordinate(i, 6), true));
        }

    }

    private void setCanStep(final List<Coordinate> coordinates) {
        for (final boolean[] canStep : canSteps) {
            Arrays.fill(canStep, false);
        }
        for (final Coordinate coordinate : coordinates) {
            canSteps[coordinate.getXPos()][coordinate.getYPos()] = true;
        }
    }

    private void greenPaintingWheretoGo(final List<Coordinate> coordinates) {
        setCanStep(coordinates);
        board.setBoardDefaultColor();
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if (canSteps[x][y]) {
                    board.getBoard(x, y).setBackground(Color.GREEN);
                }
            }
        }
    }

}
