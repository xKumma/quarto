package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.model.pieces.Piece;

public final class RuleChecker {
    public static boolean isMoveValid(Move move, Board board) {
        return board.getPieces()[move.getPosition().x()][move.getPosition().y()] == null;
    }

    public static boolean hasWon(Piece[] pieces) {
        return true;
    }
}
