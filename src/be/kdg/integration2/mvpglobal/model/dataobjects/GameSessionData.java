package be.kdg.integration2.mvpglobal.model.dataobjects;

import be.kdg.integration2.mvpglobal.model.Board;
import be.kdg.integration2.mvpglobal.model.BotDifficulty;
import be.kdg.integration2.mvpglobal.model.Move;

import java.io.Serializable;
import java.util.List;

public class GameSessionData implements Serializable {
    private final String playerName;
    private final BotDifficulty botDifficulty;
    private final List<Move> moveHistory;
    private final Board board;
    private int startingPlayer;

    // Loaded from file
    public GameSessionData(String playerName, String botDifficulty, List<Move> moveHistory) {
        this.playerName = playerName;
        this.moveHistory = moveHistory;
        this.board = null;
        this.botDifficulty = BotDifficulty.valueOf(botDifficulty.toUpperCase());
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

    public String getPlayerName() { return playerName; }
    public BotDifficulty getBotDifficulty() { return botDifficulty; }
    public List<Move> getMoveHistory() { return moveHistory; }
    public Board getBoard() { return board; }

    public String toJson() {
        return "{ \"player\": \"" + playerName + "\", \"botDifficulty\": \"" + botDifficulty + "\", \"moves\": " + moveHistory.size() + " }";
    }

    public String toXml() {
        return "<GameSession><Player>" + playerName + "</Player><Bot>" + botDifficulty + "</Bot><Moves>" + moveHistory.size() + "</Moves></GameSession>";
    }

    public static GameSessionData fromJson(String json) {
        return null;
    }

    public static GameSessionData fromXml(String xml) {
        return null;
    }

    public int getStartingPlayer() {
        return startingPlayer;
    }
}

