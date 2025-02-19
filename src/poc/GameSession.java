package poc;

import java.util.List;

public class GameSession {
    private int sessionId;

    private Board board;
    private Player currentPlayer;

    private List<Move> moves;

    public void makeMove(Move move) {
        move.setPlayer(currentPlayer);

        if (RuleChecker.isMoveValid(move, board)) {
            board.movePiece(move);

            /*
            * additional logic
            */

            moves.add(move);
        }
    }
}
