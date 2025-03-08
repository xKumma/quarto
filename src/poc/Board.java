package poc;

import be.kdg.integration2.mvpglobal.model.Position;
import be.kdg.integration2.mvpglobal.model.pieces.Piece;

import java.util.Map;

public class Board {
    private Map<Position, Piece> boardState;

    public void movePiece(Move move) {
        /*
        *   change boardState map
        */
    }

    private boolean hasConnectedFour() {
        /*
        *    check if the player has connected 4 pieces in a row, column or diagonally
        */
        return true;
    }
}
