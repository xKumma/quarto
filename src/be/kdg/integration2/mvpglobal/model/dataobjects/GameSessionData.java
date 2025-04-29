package be.kdg.integration2.mvpglobal.model.dataobjects;

import be.kdg.integration2.mvpglobal.model.Board;
import be.kdg.integration2.mvpglobal.model.BotDifficulty;
import be.kdg.integration2.mvpglobal.model.Move;
import be.kdg.integration2.mvpglobal.model.pieces.Piece;

import java.io.Serializable;
import java.util.List;

public class GameSessionData implements Serializable {
    private final String playerName;
    private final BotDifficulty botDifficulty;
    private final List<Move> moveHistory;
    private final Board board;
    private int startingPlayer;
    private Piece lastSelectedPiece;

    // Loaded from a file
    public GameSessionData(String playerName, String botDifficulty, List<Move> moveHistory, String lastSelectedPiece) {
        this.playerName = playerName;
        this.moveHistory = moveHistory;
        this.board = null;
        this.botDifficulty = BotDifficulty.valueOf(botDifficulty.toUpperCase());
        this.lastSelectedPiece = new Piece(lastSelectedPiece);
    }

    // Loaded from DB
    public GameSessionData(String playerName, String botDifficulty, String[][] board) {
        this.playerName = playerName;
        this.botDifficulty = BotDifficulty.valueOf(botDifficulty.toUpperCase());
        this.moveHistory = null;
        this.board = new Board(board);
    }

    // PreGame
    public GameSessionData(int startingPlayer, BotDifficulty difficulty) {
        this.startingPlayer = startingPlayer;
        this.botDifficulty = difficulty;

        this.moveHistory = null;
        this.board = null;
        playerName = null;
    }

    // PreSave
    public GameSessionData(String playerName, BotDifficulty difficulty, List<Move> moveHistory, Piece selectedPiece) {
        this.playerName = playerName;
        this.moveHistory = moveHistory;
        this.board = null;
        this.botDifficulty = difficulty;
        this.lastSelectedPiece = selectedPiece;
    }

    /**
     * Converts the GameSessionData object into a JSON string representation.
     * The JSON includes the player's name, bot difficulty, last selected piece,
     * and the move history (if available).
     *
     * @return A JSON string representing the current state of the GameSessionData object.
     */
    public String toJson() {
        StringBuilder sb = new StringBuilder();

        sb.append("{\n");

        sb.append("  \"playerName\": \"").append(playerName).append("\",\n");
        sb.append("  \"botDifficulty\": \"").append(botDifficulty.name()).append("\",\n");
        sb.append("  \"lastSelectedPiece\": \"").append(lastSelectedPiece).append("\",\n");

        sb.append("  \"moveHistory\": [\n");
        if (moveHistory != null) {
            for (int i = 0; i < moveHistory.size(); i++) {
                Move move = moveHistory.get(i);
                sb.append("    ").append(move.toJson());
                if (i < moveHistory.size() - 1) sb.append(",");
                sb.append("\n");
            }
        }
        sb.append("  ]\n");

        sb.append("}");

        return sb.toString();
    }

    //region Getters
    public String getPlayerName() { return playerName; }
    public BotDifficulty getBotDifficulty() { return botDifficulty; }
    public List<Move> getMoveHistory() { return moveHistory; }
    public Board getBoard() { return board; }
    public int getStartingPlayer() { return startingPlayer; }
    public Piece getSelectedPiece() { return lastSelectedPiece; }
    //endregion
}

