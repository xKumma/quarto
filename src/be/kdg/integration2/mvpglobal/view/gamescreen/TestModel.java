package be.kdg.integration2.mvpglobal.view.gamescreen;

import be.kdg.integration2.mvpglobal.model.MVPModel;
import be.kdg.integration2.mvpglobal.model.Move;
import be.kdg.integration2.mvpglobal.model.Player;
import be.kdg.integration2.mvpglobal.model.Position;
import be.kdg.integration2.mvpglobal.model.pieces.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestModel extends MVPModel {
    public Piece[][] board = new Piece[4][4];
    public List<Piece> unusedPieces = new ArrayList<>(
            Arrays.asList(
                    new Piece(PieceType.FULL, PieceColor.RED, PieceShape.ROUND, PieceSize.BIG),
                    new Piece(PieceType.FULL, PieceColor.RED, PieceShape.ROUND, PieceSize.SMALL),

                    new Piece(PieceType.FULL, PieceColor.BLUE, PieceShape.ROUND, PieceSize.BIG),
                    new Piece(PieceType.FULL, PieceColor.BLUE, PieceShape.ROUND, PieceSize.SMALL),

                    new Piece(PieceType.FULL, PieceColor.RED, PieceShape.SQUARE, PieceSize.BIG),
                    new Piece(PieceType.FULL, PieceColor.RED, PieceShape.SQUARE, PieceSize.SMALL),

                    new Piece(PieceType.FULL, PieceColor.BLUE, PieceShape.SQUARE, PieceSize.BIG),
                    new Piece(PieceType.FULL, PieceColor.BLUE, PieceShape.SQUARE, PieceSize.SMALL),

                    new Piece(PieceType.HOLLOW, PieceColor.RED, PieceShape.ROUND, PieceSize.BIG),
                    new Piece(PieceType.HOLLOW, PieceColor.RED, PieceShape.ROUND, PieceSize.SMALL),

                    new Piece(PieceType.HOLLOW, PieceColor.BLUE, PieceShape.ROUND, PieceSize.BIG),
                    new Piece(PieceType.HOLLOW, PieceColor.BLUE, PieceShape.ROUND, PieceSize.SMALL),

                    new Piece(PieceType.HOLLOW, PieceColor.RED, PieceShape.SQUARE, PieceSize.BIG),
                    new Piece(PieceType.HOLLOW, PieceColor.RED, PieceShape.SQUARE, PieceSize.SMALL),

                    new Piece(PieceType.HOLLOW, PieceColor.BLUE, PieceShape.SQUARE, PieceSize.BIG),
                    new Piece(PieceType.HOLLOW, PieceColor.BLUE, PieceShape.SQUARE, PieceSize.SMALL)
            )
    );

    public List<Move> moves = new ArrayList<>(
            Arrays.asList(
                new Move(new Player(), unusedPieces.get(6), new Position(3,2)),
                new Move(new Player(), unusedPieces.get(3), new Position(2,3)),
                new Move(new Player(), unusedPieces.get(8), new Position(0,0))
            )
    );

    public boolean MovePiece(Move move) {
        board[move.getPosition().x()][move.getPosition().y()] = move.getPiece();
        unusedPieces.remove(move.getPiece());

        return true;
    }

    public TestModel() {
        for (int i = 0; i < moves.size()-2; i++) {
            Move move = moves.get(i);
            MovePiece(move);
        }
    }

    private Move currentMove;

    public void startNewTurn() {
        currentMove = new Move();
    }

    public void endTurn() {
        if (currentMove != null) {
            currentMove.setEndTime(System.currentTimeMillis());
            moves.add(currentMove);
            currentMove = null;
        }
    }

    public long getTotalElapsedTime() {
        long elapsedTime = moves.stream().mapToLong(Move::getTime).sum();
        return elapsedTime >= 0 ? elapsedTime : 0;
    }

    public long getOngoingMoveTime() {
        return (currentMove != null) ? System.currentTimeMillis() - currentMove.getStartTime() : 0;
    }
}
