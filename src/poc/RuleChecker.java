package poc;

import java.util.List;

public final class RuleChecker {
    private RuleChecker() {} // static = no object creation allowed

    public static boolean isMoveValid(Move move, Board board) {
        /*
        *   check if position is free
        */
        return true;
    }

    public boolean checkSharedAttribute(List<Piece> pieces) {
        /*
        *   go through the pieces for each PieceAttribute
        *       check if all pieces match on the PieceAttribute
        */
        return true;
    }
}
