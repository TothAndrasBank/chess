package board;

import pieces.type.Type;

import javax.swing.*;

public enum Images {
    BLACK_KING(-Type.WHITE_KING.getValue(),new ImageIcon("C:\\Dev\\Chess\\Image\\BK.png")),
    BLACK_QUEEN(-Type.WHITE_QUEEN.getValue(),new ImageIcon("C:\\Dev\\Chess\\Image\\BQ.png")),
    BLACK_ROOK(-Type.WHITE_ROOK.getValue(),new ImageIcon("C:\\Dev\\Chess\\Image\\BR.png")),
    BLACK_KNIGHT(-Type.WHITE_KNIGHT.getValue(),new ImageIcon("C:\\Dev\\Chess\\Image\\BKn.png")),
    BLACK_BISHOP(-Type.WHITE_BISHOP.getValue(),new ImageIcon("C:\\Dev\\Chess\\Image\\BB.png")),
    BLACK_PAWN(-Type.WHITE_PAWN.getValue(),new ImageIcon("C:\\Dev\\Chess\\Image\\BP.png")),
    WHITE_KING(Type.WHITE_KING.getValue(), new ImageIcon("C:\\Dev\\Chess\\Image\\WK.png")),
    WHITE_QUEEN(Type.WHITE_QUEEN.getValue(),new ImageIcon("C:\\Dev\\Chess\\Image\\WQ.png")),
    WHITE_ROOK(Type.WHITE_ROOK.getValue(),new ImageIcon("C:\\Dev\\Chess\\Image\\WR.png")),
    WHITE_KNIGHT(Type.WHITE_KNIGHT.getValue(),new ImageIcon("C:\\Dev\\Chess\\Image\\WKn.png")),
    WHITE_BISHOP(Type.WHITE_BISHOP.getValue(),new ImageIcon("C:\\Dev\\Chess\\Image\\WB.png")),
    WHITE_PAWN(Type.WHITE_PAWN.getValue(),new ImageIcon("C:\\Dev\\Chess\\Image\\WP.png"));

    private final int value;
    private final ImageIcon imageIcon;

    Images(final int value, final ImageIcon imageIcon) {
        this.value = value;
        this.imageIcon = imageIcon;
    }


    public static ImageIcon getType(final int index) {
        return switch (index) {
            case -10 -> BLACK_KING.imageIcon;
            case -9 -> BLACK_QUEEN.imageIcon;
            case -5 -> BLACK_ROOK.imageIcon;
            case -3 -> BLACK_KNIGHT.imageIcon;
            case -2 -> BLACK_BISHOP.imageIcon;
            case -1 -> BLACK_PAWN.imageIcon;
            case 10 -> WHITE_KING.imageIcon;
            case 9 -> WHITE_QUEEN.imageIcon;
            case 5 -> WHITE_ROOK.imageIcon;
            case 3 -> WHITE_KNIGHT.imageIcon;
            case 2 -> WHITE_BISHOP.imageIcon;
            default -> WHITE_PAWN.imageIcon;
        };
    }

    public int getValue() {
        return value;
    }
}
