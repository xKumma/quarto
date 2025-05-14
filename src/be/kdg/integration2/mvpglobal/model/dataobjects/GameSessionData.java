package be.kdg.integration2.mvpglobal.model.dataobjects;

import be.kdg.integration2.mvpglobal.model.Board;
import be.kdg.integration2.mvpglobal.model.BotDifficulty;
import be.kdg.integration2.mvpglobal.model.Move;
import be.kdg.integration2.mvpglobal.model.pieces.Piece;

import java.io.Serializable;
import java.util.List;

/**
 * A data object representing the state of a game session.<br>
 * This class is used to store and retrieve game session data, including player name,
 * bot difficulty, move history, and the last selected piece.
 */
public class GameSessionData implements Serializable {
    private final String playerName;
    private final BotDifficulty botDifficulty;
    private final List<Move> moveHistory;
    private final Board board;
    private int startingPlayer;
    private Piece lastSelectedPiece;

    /**
     * Constructor for loading game session data from a file.
     *
     * @param playerName        The name of the player.
     * @param botDifficulty     The difficulty level of the bot.
     * @param moveHistory       The history of moves made during the game.
     * @param lastSelectedPiece The last selected piece in the game.
     * @param startingPlayer    The player who starts the game (1 or 2).
     */
    public GameSessionData(String playerName,
                           String botDifficulty,
                           List<Move> moveHistory,
                           String lastSelectedPiece,
                           int startingPlayer) {
        this.playerName = playerName;
        this.moveHistory = moveHistory;
        this.board = null;
        this.botDifficulty = BotDifficulty.valueOf(botDifficulty.toUpperCase());
        this.lastSelectedPiece = new Piece(lastSelectedPiece);
        this.startingPlayer = startingPlayer;
    }

    /**
     * Constructor for initializing a new game session.
     *
     * @param startingPlayer The player who starts the game (1 or 2).
     * @param difficulty     The difficulty level of the bot.
     */
    public GameSessionData(int startingPlayer, BotDifficulty difficulty) {
        this.startingPlayer = startingPlayer;
        this.botDifficulty = difficulty;

        this.moveHistory = null;
        this.board = null;
        playerName = null;
    }

    /**
     * Constructor for creating a GameSessionData object with player name, bot difficulty,
     * move history, and the last selected piece.
     *
     * @param playerName    The name of the player.
     * @param difficulty    The difficulty level of the bot.
     * @param moveHistory   The history of moves made during the game.
     * @param selectedPiece The last selected piece in the game.
     */
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
    /**
     * Gets player name.
     *
     * @return the player name
     */
    public String getPlayerName() { return playerName; }

    /**
     * Gets bot difficulty.
     *
     * @return the bot difficulty
     */
    public BotDifficulty getBotDifficulty() { return botDifficulty; }

    /**
     * Gets move history.
     *
     * @return the move history
     */
    public List<Move> getMoveHistory() { return moveHistory; }

    /**
     * Gets board.
     *
     * @return the board
     */
    public Board getBoard() { return board; }

    /**
     * Gets starting player.
     *
     * @return the starting player
     */
    public int getStartingPlayer() { return startingPlayer; }

    /**
     * Gets selected piece.
     *
     * @return the selected piece
     */
    public Piece getSelectedPiece() { return lastSelectedPiece; }
    //endregion
}

