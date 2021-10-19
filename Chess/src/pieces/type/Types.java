package pieces.type;

import java.util.List;

public enum Types {
    WHITE(List.of(Type.WHITE_KING, Type.WHITE_QUEEN, Type.WHITE_ROOK, Type.WHITE_KNIGHT, Type.WHITE_BISHOP, Type.WHITE_PAWN)),
    BLACK(List.of(Type.BLACK_KING, Type.BLACK_QUEEN, Type.BLACK_ROOK, Type.BLACK_KNIGHT, Type.BLACK_BISHOP, Type.BLACK_PAWN));

   private final List<Type> types ;

    Types(final List<Type> types) {
        this.types = types;
    }

    public List<Type> getTypes() {
        return types;
    }
}
