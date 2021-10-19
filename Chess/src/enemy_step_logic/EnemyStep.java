//package enemy_step_logic;
//
//
//import pieces.Pieces;
//import pieces.move.Move;
//import pieces.move.tools.Coordinate;
//import pieces.Piece;
//import pieces.type.Type;
//import pieces.type.Types;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//public class EnemyStep {
//    final static int BOARD_SIZE = 8;
//    boolean[][] playerHitZone = new boolean[BOARD_SIZE][BOARD_SIZE];
//    List<Pieces> playerPieces = new ArrayList<>(16);
//    List<Pieces> enemyPieces = new ArrayList<>(16);
//
//    public EnemyStep(final List<Pieces> playerPieces, final List<Pieces> enemyPieces) {
//        this.playerPieces.addAll(playerPieces);
//        this.enemyPieces.addAll(enemyPieces);
//    }
//
//
//    public Step nextStep(final int[][] location, final Step step, final List<Pieces> playerPieces, final List<Pieces> enemyPieces) {
//
//        System.out.println("-------------------START--------------------------");
//        refreshPieces(step, playerPieces, enemyPieces);
//        playerHitZone = calculatePlayerHitZones(location, playerPieces);
//        Step nextStep = canMakeCheckMate(location);
//        if (Objects.nonNull(nextStep)) {
//            return nextStep;
//        }
//
//        if (isCheck()) {
//            System.out.println("CHECK!");
//            nextStep = eliminateTheCheck(location, true);
//            System.out.println("eliminateTheCheck(location, true)");
//            if (Objects.nonNull(nextStep)) {
//                return nextStep;
//            }
//            nextStep = stepOutInCheck(location);
//            System.out.println("nextStep = stepOutInCheck(location)");
//            if (Objects.nonNull(nextStep)) {
//                return nextStep;
//            }
//            nextStep = eliminateTheCheck(location, false);
//            System.out.println("eliminateTheCheck(location, false)");
//            if (Objects.nonNull(nextStep)) {
//                return nextStep;
//            }
//            System.out.println("Check Mate!!");
//        } else {
//            nextStep = defensiveHit(location, Type.WHITE_QUEEN.getValue());
//            System.out.println("defensiveHit(location, Type.WHITE_QUEEN.getValue())");
//            if (Objects.nonNull(nextStep)) {
//                return nextStep;
//            }
//            System.out.println("hitByValue(location, Type.WHITE_QUEEN.getValue()");
//            nextStep = hitByValue(location, Type.WHITE_QUEEN.getValue());
//            if (Objects.nonNull(nextStep)) {
//                return nextStep;
//            }
//            System.out.println("defensiveHit(location, Type.WHITE_ROOK.getValue()");
//            nextStep = defensiveHit(location, Type.WHITE_ROOK.getValue());
//            if (Objects.nonNull(nextStep)) {
//                return nextStep;
//            }
//            System.out.println("defensiveHit(location, Type.WHITE_KNIGHT.getValue()");
//            nextStep = defensiveHit(location, Type.WHITE_KNIGHT.getValue());
//            if (Objects.nonNull(nextStep)) {
//                return nextStep;
//            }
//            System.out.println("defensiveHit(location, Type.WHITE_BISHOP.getValue()");
//            nextStep = defensiveHit(location, Type.WHITE_BISHOP.getValue());
//            if (Objects.nonNull(nextStep)) {
//                return nextStep;
//            }
//            System.out.println("hitByValue(location, Type.WHITE_ROOK.getValue()");
//            nextStep = hitByValue(location, Type.WHITE_ROOK.getValue());
//            if (Objects.nonNull(nextStep)) {
//                return nextStep;
//            }
//            System.out.println("hitByValue(location, Type.WHITE_KNIGHT.getValue()");
//            nextStep = hitByValue(location, Type.WHITE_KNIGHT.getValue());
//            if (Objects.nonNull(nextStep)) {
//                return nextStep;
//            }
//            System.out.println("hitByValue(location, Type.WHITE_BISHOP.getValue()");
//            nextStep = hitByValue(location, Type.WHITE_BISHOP.getValue());
//            if (Objects.nonNull(nextStep)) {
//                return nextStep;
//            }
//            System.out.println("defensiveHit(location, Type.WHITE_PAWN.getValue()");
//            nextStep = defensiveHit(location, Type.WHITE_PAWN.getValue());
//            if (Objects.nonNull(nextStep)) {
//                return nextStep;
//            }
//            System.out.println();
//        }
//
//
//        inDangerousZone(Type.BLACK_QUEEN.getValue());
//
//        System.out.println("-------------------END--------------------------");
//        return null;
//    }
//
//
//    private boolean[][] calculatePlayerHitZones(final int[][] location, final List<Pieces> playerPieces) {
//        final boolean[][] playerHitZone = new boolean[BOARD_SIZE][BOARD_SIZE];
//        for (final Pieces piece : playerPieces) {
//            for (final Coordinate coordinate : piece.getHitZone(location)) {
//                playerHitZone[coordinate.getXPos()][coordinate.getYPos()] = true;
//            }
//        }
//        return playerHitZone;
//    }
//
//    public void refreshPieces(final Step step, final List<Pieces> playerPieces, final List<Pieces> enemyPieces) {
//        this.playerPieces.clear();
//        this.playerPieces.addAll(playerPieces);
//        this.enemyPieces.clear();
//        this.enemyPieces.addAll(enemyPieces);
//    }
//
//    public Step canMakeCheckMate(final int[][] location) {
//        final int[][] newLocation = getLocationClone(location);
//        for (final Pieces enemyPiece : enemyPieces) {
//            for (final Coordinate step : enemyPiece.getHitZone(location)) {
//
//                newLocation[step.getXPos()][step.getYPos()] = enemyPiece.getValue();
//                newLocation[enemyPiece.getCoordinate().getXPos()][enemyPiece.getCoordinate().getYPos()] = 0;
//                final Step newStep = new Step(enemyPiece.getCoordinate(), step, enemyPiece.getValue(), newLocation[step.getXPos()][step.getYPos()]);
//                if (isPlayerKingCheckMate(newLocation)) {
//                    if (canPlayerHitThisPiece(newLocation, newStep)) {
//                        return canStepThatTheKinkIsNotInCheck(newLocation, newStep) ? newStep : null;
//                    }
//                }
//                newLocation[step.getXPos()][step.getYPos()] = location[step.getXPos()][step.getYPos()];
//                newLocation[enemyPiece.getCoordinate().getXPos()][enemyPiece.getCoordinate().getYPos()] = location[enemyPiece.getCoordinate().getXPos()][enemyPiece.getCoordinate().getYPos()];
//
//            }
//        }
//        return null;
//    }
//
//    public boolean canPlayerHitThisPiece(final int[][] location, final Step step) {
//        final boolean[][] playerHitZone = calculatePlayerHitZones(location, playerPieces);
//        return !playerHitZone[step.getNewCoordinate().getXPos()][step.getNewCoordinate().getYPos()];
//    }
//
//    public boolean isPlayerKingCheckMate(final int[][] location) {
//        final Pieces whitKing = getPiecesByValue(Type.WHITE_KING.getValue());
//        final Move move = new Move();
//        final boolean[][] enemyHitZone = move.getEnemyHitZone(location);
//        final int whiteKingXPos = whitKing.getCoordinate().getXPos();
//        final int whiteKingYPos = whitKing.getCoordinate().getYPos();
//
//        return whitKing.getMoveZone(location).size() == 0 && enemyHitZone[whiteKingXPos][whiteKingYPos];
//    }
//
//    public Step eliminateTheCheck(final int[][] location, final boolean isSafe) {
//        Step newStep;
//        for (final Pieces enemyPiece : enemyPieces) {
//            for (final Coordinate step : enemyPiece.getHitZone(location)) {
//                if (enemyPiece.getValue() == Type.BLACK_KING.getValue()) {
//                    continue;
//                }
//                final int x = step.getXPos();
//                final int y = step.getYPos();
//                if (isSafe && playerHitZone[x][y]) {
//                    continue;
//                }
//                if (location[x][y] >= 0) {
//                    newStep = new Step(enemyPiece.getCoordinate(), step, enemyPiece.getValue(), location[x][y]);
//                    if (canStepThatTheKinkIsNotInCheck(location, newStep)) {
//                        return newStep;
//                    }
//                }
//            }
//        }
//        return null;
//    }
//
//    public Step stepOutInCheck(final int[][] location) {
//        Step newStep;
//        final List<Step> stepList = new ArrayList<>();
//        final Pieces blackKing = getPiecesByValue(Type.BLACK_KING.getValue());
//        for (final Coordinate step : blackKing.getHitZone(location)) {
//            final int x = step.getXPos();
//            final int y = step.getYPos();
//            newStep = new Step(blackKing.getCoordinate(), step, blackKing.getValue(), location[x][y]);
//            if (location[x][y] >= 0 && canStepThatTheKinkIsNotInCheck(location, newStep)) {
//                stepList.add(newStep);
//            }
//        }
//        Step maxValueHitStep = stepList.size() > 0 ? stepList.get(0) : null;
//        for (final Step step : stepList) {
//            if (step.getStepLocationValue() > maxValueHitStep.getStepLocationValue()) {
//                maxValueHitStep = step;
//            }
//        }
//        return maxValueHitStep;
//    }
//
//    public Step hitByValue(final int[][] location, final int value) {
//        final Step step;
//        for (final Pieces enemyPiece : enemyPieces) {
//            if (enemyPiece.getReversValue() >= value) {
//                continue;
//            }
//            for (final Coordinate coordinate : enemyPiece.getHitZone(location)) {
//                final int x = coordinate.getXPos();
//                final int y = coordinate.getYPos();
//                if (location[x][y] == value) {
//                    step = new Step(enemyPiece.getCoordinate(), coordinate, enemyPiece.getValue(), value);
//                    return canStepThatTheKinkIsNotInCheck(location, step) ? step : null;
//                }
//            }
//        }
//        return null;
//    }
//
//
//    public int[][] getLocationClone(final int[][] location) {
//        final int[][] newLocation = new int[BOARD_SIZE][BOARD_SIZE];
//        for (int i = 0; i < BOARD_SIZE; i++) {
//            System.arraycopy(location[i], 0, newLocation[i], 0, location[i].length);
//        }
//        return newLocation;
//    }
//
//    public Step defensiveHit(final int[][] location, final int value) {
//        final Step step;
//        for (final Pieces enemyPiece : enemyPieces) {
//            for (final Coordinate coordinate : enemyPiece.getHitZone(location)) {
//                final int x = coordinate.getXPos();
//                final int y = coordinate.getYPos();
//                final int[][] newLocation = getLocationClone(location);
//                newLocation[x][y] = enemyPiece.getValue();
//                newLocation[enemyPiece.getCoordinate().getXPos()][enemyPiece.getCoordinate().getYPos()] = 0;
//                final boolean[][] playerHitZone = calculatePlayerHitZones(newLocation, playerPieces);
//                if (location[x][y] == value && !playerHitZone[x][y]) {
//                    step = new Step(enemyPiece.getCoordinate(), coordinate, enemyPiece.getValue(), value);
//                    return canStepThatTheKinkIsNotInCheck(location, step) ? step : null;
//                }
//            }
//        }
//
//        return null;
//    }
//
//    public boolean inDangerousZone(final int value) {
//        boolean isDangerous = false;
//        for (final Pieces enemyPiece : enemyPieces) {
//            if (enemyPiece.getValue() == value) {
//                isDangerous = playerHitZone[enemyPiece.getCoordinate().getXPos()][enemyPiece.getCoordinate().getYPos()];
//            }
//            if (isDangerous) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    public boolean isCheck() {
//        final Pieces blackKing = getPiecesByValue(Type.BLACK_KING.getValue());
//        System.out.println(blackKing.getCoordinate());
//
//        for (int y = 0; y < BOARD_SIZE; y++) {
//            for (int x = 0; x < BOARD_SIZE; x++) {
////                System.out.print("x:" + x + " y:" + y+"==>"+playerHitZone[x][y]);
//                System.out.printf("|x:%d y:%d ==> %6b |", x, y, playerHitZone[x][y]);
//            }
//            System.out.println();
//        }
//        return playerHitZone[blackKing.getCoordinate().getXPos()][blackKing.getCoordinate().getYPos()];
//    }
//
//
//    public boolean canStepThatTheKinkIsNotInCheck(final int[][] location, final Step newStep) {
//        if (Objects.isNull(newStep)) {
//            System.out.print("Step is null    ---");
//            return false;
//        }
//        final List<Type> types = new ArrayList<>(Types.WHITE.getTypes());
//
//        final int[][] newLocation = getLocationClone(location);
//        newLocation[newStep.getNewCoordinate().getXPos()][newStep.getNewCoordinate().getYPos()] = newStep.getMovingPieceValue();
//        newLocation[newStep.getOldCoordinate().getXPos()][newStep.getOldCoordinate().getYPos()] = 0;
//        final Pieces king = getPiecesByValue(Type.BLACK_KING.getValue());
//
//        if (newStep.getMovingPieceValue() == Type.BLACK_KING.getValue()) {
//            king.setCoordinate(newStep.getNewCoordinate());
//        }
//        for (final Type type : types) {
//            if (canHitTheGivenPiece(newLocation, king.getCoordinate(), type)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//
//    public boolean canHitTheGivenPiece(final int[][] newLocation, final Coordinate givenPieceCoordinate, final Type hitterPiece) {
//        final Pieces pieces = new Piece(hitterPiece, givenPieceCoordinate);
//        for (final Coordinate step : pieces.getHitZone(newLocation)) {
//            if (newLocation[step.getXPos()][step.getYPos()] == hitterPiece.getValue()) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//    public Pieces getPiecesByValue(final int value) {
//        if (value > 0) {
//            for (final Pieces playerPiece : playerPieces) {
//                if (playerPiece.getValue() == value) {
//                    return new Piece(Type.getType(value), playerPiece.getCoordinate());
//                }
//            }
//        } else {
//            for (final Pieces enemyPiece : enemyPieces) {
//                if (enemyPiece.getValue() == value) {
//                    return new Piece(Type.getType(value), enemyPiece.getCoordinate());
//                }
//            }
//        }
//
//        return null;
//    }
//
//    public void print(final int[][] board) {
//        for (int x = 0; x < BOARD_SIZE; x++) {
//            for (int y = 0; y < BOARD_SIZE; y++) {
//                System.out.printf("%3d", board[x][y]);
//            }
//            System.out.println();
//        }
//    }
//}
//
//
////          ---------------------------Print-----------------------------------
////        for (int x = 0; x < BOARD_SIZE; x++) {
////        for (int y = 0; y < BOARD_SIZE; y++) {
////        System.out.printf("%3d",hitZone[x][y]);
////        }
////        System.out.println();
////        }// ---------------------------Print-----------------------------------
//
//
////{
////        enemyPieces.add(new Piece(Type.ROOK, new Coordinate(0, 0), false));
////        enemyPieces.add(new Piece(Type.KNIGHT, new Coordinate(1, 0), false));
////        enemyPieces.add(new Piece(Type.BISHOP, new Coordinate(2, 0), false));
////        enemyPieces.add(new Piece(Type.QUEEN, new Coordinate(3, 0), false));
////        enemyPieces.add(new Piece(Type.KING, new Coordinate(4, 0), false));
////        enemyPieces.add(new Piece(Type.BISHOP, new Coordinate(5, 0), false));
////        enemyPieces.add(new Piece(Type.KNIGHT, new Coordinate(6, 0), false));
////        enemyPieces.add(new Piece(Type.ROOK, new Coordinate(7, 0), false));
////    }