package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.model.dataobjects.GameSessionData;
import be.kdg.integration2.mvpglobal.model.eventlisteners.GameSessionListener;
import be.kdg.integration2.mvpglobal.model.pieces.*;

import java.util.*;

public class GameSession implements BaseModel {

    private ComputerPlayer computer;
    private HumanPlayer player;
    private boolean isPlayersTurn;
    private TurnPhase turnPhase;

    private Board board;
    private final Map<String,Piece> unusedPieces = new HashMap<>();

    private List<Move> moves = new ArrayList<>();
    private Piece selectedPiece;
    private Move currentMove;

    private GameSessionListener listener;

    public GameSession () {
        this.board = new Board();
        this.player = new HumanPlayer();
        this.computer = new ComputerPlayer();
        //...
    }

    @Override
    public void init(Object data) {
        GameSessionData sessionData = (GameSessionData) data;

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

        moves = sessionData.getMoveHistory() != null ? sessionData.getMoveHistory() : new ArrayList<>(
                // temp
//                Arrays.asList(
//                        new Move(new Player(), unusedPieces.get("round_big_hollow#red"), new PositionData(3,2)),
//                        new Move(new Player(), unusedPieces.get("square_small_hollow#blue"), new PositionData(2,3)),
//                        new Move(new Player(), unusedPieces.get("round_big_full#red"), new PositionData(0,0))
//                )
        );
        playMoves(moves);

        List<Piece> pieceList = new ArrayList<>(unusedPieces.values());;
        selectedPiece = pieceList.get(new Random().nextInt(pieceList.size()));

        //startNewTurn();
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
        unusedPieces.remove(move.getPiece().toString());

        return true;
    }

    public void play(){
        //...

        // determine move AI:
        Move move = computer.getMove(this);
        // ...
    }

    private void createPieces() {
        for (PieceType type : PieceType.values()) {
            for (PieceColor color : PieceColor.values()) {
                for (PieceShape shape : PieceShape.values()) {
                    for (PieceSize size : PieceSize.values()) {
                        Piece piece = new Piece(type, color, shape, size);
                        unusedPieces.put(piece.toString(), piece);
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
        if (!unusedPieces.containsKey(pieceSlug)) return false;

        selectedPiece = unusedPieces.get(pieceSlug);

        return true;
    }

    public void selectPosition(int x, int y) {
        currentMove.setPosition(x,y);

        if (!movePiece(currentMove)) return;

        listener.onMoveSuccessful(currentMove);

        turnPhase = TurnPhase.PICKING;
    }

    public void endTurn() {
        //isPlayersTurn = !isPlayersTurn;

        endMove();

        System.out.println("End turn ; " + isPlayersTurn);
    }


    public long getTotalElapsedTime() {
        long elapsedTime = moves.stream().mapToLong(Move::getTime).sum();
        return elapsedTime >= 0 ? elapsedTime : 0;
    }

    public long getOngoingMoveTime() {
        return (currentMove != null) ? System.currentTimeMillis() - currentMove.getStartTime() : 0;
    }

    public int getRoundsCount() {
        return moves.size()+1;
    }

    public Move getCurrentMove() { return currentMove; }

    public Board getBoard () {return board;}

    public Map<String, Piece> getUnusedPieces() {
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

    public void setListener(GameSessionListener listener) {
        this.listener = listener;
    }
}