package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.model.dataobjects.GameSessionData;
import be.kdg.integration2.mvpglobal.model.dataobjects.PositionData;
import be.kdg.integration2.mvpglobal.model.pieces.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameSession implements BaseModel {

    ComputerPlayer computer;
    HumanPlayer player;
    Board board;

    public GameSession () {
        this.board = new Board();
        this.player = new HumanPlayer();
        this.computer = new ComputerPlayer();
        //...
    }

    public GameSession (GameSessionData gameSession) {
        System.out.println(gameSession.getStartingPlayer() + " " + gameSession.getBotDifficulty());
    }

    public void play(){
        //...

        // determine move AI:
        Move move = computer.getMove(this);
        // ...
    }

    public Board getBoard () {return board;}

    public Piece[][] getBoard1() {
        return board1;
    }

    public List<Piece> getUnusedPieces() {
        return unusedPieces;
    }

    public Piece[][] board1 = new Piece[4][4];
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
                    new Move(new Player(), unusedPieces.get(6), new PositionData(3,2)),
                    new Move(new Player(), unusedPieces.get(3), new PositionData(2,3)),
                    new Move(new Player(), unusedPieces.get(8), new PositionData(0,0))
            )
    );

    public boolean MovePiece(Move move) {
        board1[move.getPosition().x()][move.getPosition().y()] = move.getPiece();
        unusedPieces.remove(move.getPiece());

        return true;
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