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
        PieceAttribute[] t = PieceAttribute.values();
        for (int i = 0; i < 4; i++) {
            if (pieces[0].getOneAttribute(t[i]) == pieces[1].getOneAttribute(t[i]) && pieces[0].getOneAttribute(t[i]) == pieces[2].getOneAttribute(t[i]) && pieces[0].getOneAttribute(t[i]) == pieces[3].getOneAttribute(t[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean fourInARow(Board board) {
        //
        Piece[] fourInARow = new Piece[4];
        boolean fourInARowFound = false;

        //check if there are four pieces in a row diagonal1
        for (int i = 0; i < 4; i++) {
            if (board.getPieces()[i][i] != null) {
                fourInARow[i] = board.getPieces()[i][i];
                if (i == 3) fourInARowFound = true;
            } else {
                fourInARowFound = false;
                break;
            }
        }
        if (fourInARowFound) {
            if(hasWon(fourInARow)) return true;
        }

        //reset temp values
        for (int i = 0; i < 4; i++) {
            fourInARow[i] = null;
        }

        //check if four in a row diagonal2
        for (int i = 0; i < 4; i++) {
            if (board.getPieces()[i][3-i] != null) {
                fourInARow[i] = board.getPieces()[i][3-i];
                if (i == 3) fourInARowFound = true;
            } else {
                fourInARowFound = false;
                break;
            }
        }
        if (fourInARowFound) {
            if(hasWon(fourInARow)) return true;
        }

        //reset temp values
        for (int i = 0; i < 4; i++) {
            fourInARow[i] = null;
        }

        //check four in a row in rows
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board.getPieces()[i][j] != null) {
                    fourInARow[i] = board.getPieces()[i][j];
                    if (i == 3) fourInARowFound = true;
                } else {
                    fourInARowFound = false;
                    break;
                }
            }
            if (fourInARowFound) {
                if(hasWon(fourInARow)) return true;
            }
            //reset temp values
            for (int k = 0; i < 4; i++) {
                fourInARow[k] = null;
            }
        }

        //check four in a row in columns
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board.getPieces()[j][i] != null) {
                    fourInARow[i] = board.getPieces()[j][i];
                    if (i == 3) fourInARowFound = true;
                } else {
                    fourInARowFound = false;
                    break;
                }
            }
            if (fourInARowFound) {
                if (hasWon(fourInARow)) return true;
            }
            //reset temp values
            for (int k = 0; i < 4; i++) {
                fourInARow[k] = null;
            }
        }
        return false;
    }
}
