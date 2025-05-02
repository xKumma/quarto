package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.model.dataobjects.GameSessionData;
import be.kdg.integration2.mvpglobal.model.dataobjects.PositionData;
import be.kdg.integration2.mvpglobal.model.pieces.*;
import be.kdg.integration2.mvpglobal.utility.Router;

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
        this.player = HumanPlayer.getInstance();
        if (player == null) {
            player = new HumanPlayer("player");
        }
        this.computer = new ComputerPlayer();
        //...
    }

    @Override
    public void init(Object data) {
        GameSessionData sessionData = (GameSessionData) data;
        computer.setDifficulty(sessionData.getBotDifficulty());

        if (sessionData.getStartingPlayer() == 0) {
            isPlayersTurn = new Random().nextBoolean();
        } else if (sessionData.getStartingPlayer() == 1) {
            isPlayersTurn = true;
        } else {
            isPlayersTurn = false;
        }

        board = sessionData.getBoard() != null ? sessionData.getBoard() : new Board();

        createPieces();

        moves = (sessionData.getMoveHistory() != null) ? sessionData.getMoveHistory() : new ArrayList<>();

        if (!moves.isEmpty()) {
            playMoves(moves);
            selectedPiece = sessionData.getSelectedPiece();
        }
        else {

            selectedPiece = getUnusedPieces().get(new Random().nextInt(unusedPieces.size()));
        }

        active = true;
    }

    /**
     * Plays a list of moves on the board.
     * This method iterates through the provided moves and executes each one.
     *
     * @param moves The list of moves to be played.
     */
    private void playMoves(List<Move> moves) {
        for (Move move : moves) {
            movePiece(move);
        }
    }

    /**
     * Moves a piece on the board according to the specified move.
     * Validates the move using the RuleChecker and updates the board state.<br>
     * If the move is valid, it removes the piece from the unused pieces list,
     * updates the board, and checks if the game should end.
     *
     * @param move The Move object representing the piece and its destination.
     * @return true if the move is valid and successfully executed, false otherwise.
     */
    public boolean movePiece(Move move) {
        // Validate
        if (!RuleChecker.isMoveValid(move, board)) {
            return false;
        }

        board.movePiece(move);
        unusedPieces.remove(move.getPiece());

        checkGameOver();

        return true;
    }

    /**
     * Checks if the game is over by verifying win conditions or if all pieces are used.
     * If a win condition is met or no unused pieces remain, the game ends.
     */
    private void checkGameOver() {
        if (RuleChecker.fourInARow( board) || unusedPieces.isEmpty()) {
            endGame();
        }
    }

    /**
     * Creates all possible pieces for the game by iterating through
     * all combinations of PieceType, PieceColor, PieceShape, and PieceSize.<br>
     * Adds the created pieces to the unusedPieces list.
     */
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

    /**
         * Starts a new turn by initializing a new move with the currently selected piece
         * and setting the turn phase to PLACING.
         */
    public void startNewTurn() {
        currentMove = new Move(isPlayersTurn ? player.getName() : "bot" ,selectedPiece);
        turnPhase = TurnPhase.PLACING;
    }


    /**
     * Ends the current move by setting its end time and adding it to the move history.
     * If no current move exists, the method does nothing.
     * A new empty move is initialized after ending the current one.
     */
    public void endMove() {
        if (currentMove == null) return;

        currentMove.setEndTime(System.currentTimeMillis());
        moves.add(currentMove);

        if (RuleChecker.fourInARow(board)) Router.getInstance().goTo(Screen.END_SCREEN, null);
        currentMove = new Move();
    }

    /**
     * Selects a piece from the unused pieces list based on the provided slug.
     * If the piece is found, it sets it as the selected piece and returns true.
     * Otherwise, it returns false.
     *
     * @param pieceSlug The slug of the piece to select.
     * @return true if the piece was successfully selected, false otherwise.
     */
    public boolean selectPiece(String pieceSlug) {
        for (Piece piece : unusedPieces) {
            if (piece.toString().equals(pieceSlug)) {
                selectedPiece = piece;
                return true;
            }
        }
        return false;
    }

    /**
     * Selects a piece from the unused pieces list based on the provided index.
     * If the index is valid, it sets the piece at the specified index as the selected piece.
     *
     * @param index The index of the piece to select.
     * @return true if the piece was successfully selected, false if the index is out of bounds.
     */
    public boolean selectPiece(int index) {
        if (index >= unusedPieces.size()) return false;

        selectedPiece = unusedPieces.get(index);
        return true;
    }


    /**
     * Selects a position on the board for the current move.
     * Updates the position of the current move and attempts to execute it.<br>
     * If the move is invalid or no position was provided, the method returns false.
     * If the move is valid, the turn phase is updated to PICKING.
     *
     * @param positionData The position data containing x and y coordinates.
     * @return true if the move is valid and successfully executed, false otherwise.
     */
    public boolean selectPosition(PositionData positionData) {
        if (positionData == null) return false;
        currentMove.setPosition(positionData.x(), positionData.y());

        if (!movePiece(currentMove)) return false;

        turnPhase = TurnPhase.PICKING;

        return true;
    }

    /**
     * Ends the current turn by toggling the player's turn status and finalizing the current move.
     */
    public void endTurn() {
        isPlayersTurn = !isPlayersTurn;
        endMove();
    }

    /**
     * Ends the game by setting the active status to false.
     * This method is called when the game reaches its conclusion,
     * such as when all pieces have been used or a win condition is met.
     */
    private void endGame() {
        active = false;
    }


    // region Getters
    public boolean isActive() {
        return active;
    }

    public boolean isPlayersTurn() {
        return isPlayersTurn;
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

    public TurnPhase getTurnPhase() {
        return turnPhase;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public ComputerPlayer getComputerPlayer() {
        return computer;
    }

    public GameSessionData getSessionData() {
        return new GameSessionData(player.getName(), computer.getDifficulty(), moves, selectedPiece);
    }
    // endregion
}