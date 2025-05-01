package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.model.pieces.Piece;
import be.kdg.integration2.mvpglobal.model.pieces.PieceAttribute;

import java.util.HashMap;
import java.util.Map;

public final class RuleChecker {
    public static boolean isMoveValid(Move move, Board board) {
        return board.getPieces()[move.getPosition().x()][move.getPosition().y()] == null;
    }

    public static boolean hasWon(Piece[] pieces) {
        if (pieces == null || pieces.length != 4) return false;

        PieceAttribute[] t = PieceAttribute.values();

        for (int i = 0; i < 4; i++) {
            if (pieces[0] != null && pieces[1] != null && pieces[2] != null && pieces[3] != null) {
                String attr0 = (pieces[0].getOneAttribute(t[i]));
                String attr1 = (pieces[1].getOneAttribute(t[i]));
                String attr2 = (pieces[2].getOneAttribute(t[i]));
                String attr3 = (pieces[3].getOneAttribute(t[i]));

                if (attr0.equals(attr1)  && attr0.equals(attr2) && attr0.equals(attr3)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean fourInARow(Board board) {
        Piece[] fourInARow = new Piece[4];
        boolean fourInARowFound;

        // Diagonal 1
        fourInARowFound = true;
        for (int i = 0; i < 4; i++) {
            fourInARow[i] = board.getPieces()[i][i];
            if (fourInARow[i] == null) fourInARowFound = false;
        }
        if (fourInARowFound && hasWon(fourInARow)) return true;

        // Diagonal 2
        fourInARowFound = true;
        for (int i = 0; i < 4; i++) {
            fourInARow[i] = board.getPieces()[i][3 - i];
            if (fourInARow[i] == null) fourInARowFound = false;
        }
        if (fourInARowFound && hasWon(fourInARow)) return true;

        // Rows
        for (int row = 0; row < 4; row++) {
            fourInARowFound = true;
            for (int col = 0; col < 4; col++) {
                fourInARow[col] = board.getPieces()[row][col];
                if (fourInARow[col] == null) fourInARowFound = false;
            }
            if (fourInARowFound && hasWon(fourInARow)) return true;
        }

        // Columns
        for (int col = 0; col < 4; col++) {
            fourInARowFound = true;
            for (int row = 0; row < 4; row++) {
                fourInARow[row] = board.getPieces()[row][col];
                if (fourInARow[row] == null) fourInARowFound = false;
            }
            if (fourInARowFound && hasWon(fourInARow)) return true;
        }

        return false;
    }


}
