package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.model.dataobjects.GameSessionData;
import be.kdg.integration2.mvpglobal.model.dataobjects.PositionData;
import be.kdg.integration2.mvpglobal.model.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameSession implements BaseModel {

    private ComputerPlayer computer;
    private HumanPlayer player;

    private boolean isPlayersTurn;
    private TurnPhase turnPhase;
    public boolean active;

    private Board board;
    private final List<Piece> unusedPieces = new ArrayList<>();

    private List<Move> moves = new ArrayList<>();
    private Piece selectedPiece;
    private Move currentMove;

    public GameSession () {
        //this.board = new Board();
        this.player = new HumanPlayer();
        this.computer = new ComputerPlayer();
        //...
    }

    @Override
    public void init(Object data) {
        GameSessionData sessionData = (GameSessionData) data;
        System.out.println(sessionData.toJson());

        computer.setDifficulty(sessionData.getBotDifficulty());

        if (sessionData.getStartingPlayer() == 0) {
            isPlayersTurn = new Random().nextBoolean();
        } else if (sessionData.getStartingPlayer() == 1) {
            isPlayersTurn = true;
        } else {
            isPlayersTurn = false;
        }

        System.out.println("PlayersTurn: " + isPlayersTurn);

        board = sessionData.getBoard() != null ? sessionData.getBoard() : new Board();

        createPieces();

        moves = (sessionData.getMoveHistory() != null) ? sessionData.getMoveHistory() : new ArrayList<>();
        if (!moves.isEmpty()) {
            playMoves(moves);
            selectedPiece = sessionData.getSelectedPiece();
        }
        else selectedPiece = getUnusedPieces().get(new Random().nextInt(unusedPieces.size()));

        active = true;
    }

    private void playMoves(List<Move> moves) {
        for (Move move : moves) {
            movePiece(move);
        }
    }

    public boolean movePiece(Move move) {
        // Validate
        if (!RuleChecker.isMoveValid(move, board)) {
            System.out.println("Invalid move");
            return false;
        }

        board.movePiece(move);
        unusedPieces.remove(move.getPiece());
        System.out.println("Unused left: " + unusedPieces.size());
        if (unusedPieces.isEmpty()) endGame();
        return true;
    }

    public void play(){
        //...

        // determine move AI:
        //Move move = computer.getMove(this);
        // ...
    }

    private void createPieces() {
        for (PieceType type : PieceType.values()) {
            for (PieceColor color : PieceColor.values()) {
                for (PieceShape shape : PieceShape.values()) {
                    for (PieceSize size : PieceSize.values()) {
                        Piece piece = new Piece(type, color, shape, size);
                        unusedPieces.add(piece);
                    }
                }
            }
        }
    }

    public void startNewTurn() {
        currentMove = new Move(selectedPiece);
        turnPhase = TurnPhase.PLACING;

        System.out.println("---------------------------------");
        System.out.println("moves: " + moves.size());

        System.out.println("Selected Piece: " + currentMove.getPiece().toString());
    }


    public void endMove() {
        if (currentMove == null) return;

        currentMove.setEndTime(System.currentTimeMillis());
        moves.add(currentMove);

        currentMove = new Move();
    }

    public boolean selectPiece(String pieceSlug) {
        for (Piece piece : unusedPieces) {
            if (piece.toString().equals(pieceSlug)) {
                selectedPiece = piece;
                return true;
            }
        }
        return false;
    }

    // select by index, if you prefer generating an index rather than getting a slug from the piece
    // might be useful for future AI
    public boolean selectPiece(int index) {
        if (index >= unusedPieces.size()) return false;

        selectedPiece = unusedPieces.get(index);
        return true;
    }


    public boolean selectPosition(PositionData positionData) {
        currentMove.setPosition(positionData.x(), positionData.y());

        if (!movePiece(currentMove)) return false;

        turnPhase = TurnPhase.PICKING;

        return true;
    }

    public void endTurn() {
        isPlayersTurn = !isPlayersTurn;

        endMove();

        System.out.println("End turn ; " + isPlayersTurn);
    }

    private void endGame() {
        active = false;

        System.out.println("End game");
    }


    public long getTotalElapsedTime() {
        long elapsedTime = moves.stream().mapToLong(Move::getTime).sum();
        return elapsedTime >= 0 ? elapsedTime : 0;
    }

    public long getOngoingMoveTime() {
        return (currentMove != null) ? System.currentTimeMillis() - currentMove.getStartTime() : 0;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public int getRoundsCount() {
        return moves.size()+1;
    }

    public Move getCurrentMove() { return currentMove; }

    public Board getBoard () {return board;}

    public List<Piece> getUnusedPieces() {
        return unusedPieces;
    }

    public boolean isPlayersTurn() {
        return isPlayersTurn;
    }

    public TurnPhase getTurnPhase() {
        return turnPhase;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public ComputerPlayer getComputerPlayer() {
        return computer;
    }

    public boolean isActive() {
        return active;
    }

    public GameSessionData getSessionData() {
        return new GameSessionData("player.name", computer.getDifficulty(), moves, selectedPiece);
    }
}