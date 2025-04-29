package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.model.dataobjects.PositionData;
import be.kdg.integration2.mvpglobal.model.pieces.Piece;

/**
 * Represents a move in the game, including details about the player,
 * the piece being moved, its position, and the timing of the move.
 */
public class Move {
    private String player;
    private Piece piece;
    private PositionData position;

    private long startTime;
    private long endTime;

    public Move(){}

    public Move(Player player, Piece piece, PositionData position) {
        this.player = player.toString();
        this.piece = piece;
        this.position = position;
        this.startTime = System.currentTimeMillis();
    }

    public Move(String player, Piece piece, PositionData position, long startTime, long endTime) {
        this.player = player;
        this.piece = piece;
        this.position = position;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Move(String player, Piece selectedPiece) {
        this.player = player;
        this.piece = selectedPiece;
        this.startTime = System.currentTimeMillis();
    }

    public String toJson() {
        return String.format("{\"player\":%s,\"x\":%d,\"y\":%d,\"pieceSlug\":%s,\"startTime\":%d, \"endTime\":%d}",
                player, position.x(), position.y(), piece.toString(), startTime, endTime);
    }

    // region Getters and Setters
    public long getTime() { return endTime - startTime; }
    public long getStartTime() { return startTime; }
    public long getEndTime() { return endTime;}

    public String getPlayer() { return player; }

    public Piece getPiece() { return piece; }

    public PositionData getPosition() { return position; }
    public void setPlayer(Player player) { this.player = player.toString(); }

    public void setEndTime(long endTime) { this.endTime = endTime; }

    public void setPiece(Piece piece) { this.piece = piece;
    }

    public void setPosition(int x, int y) { position = new PositionData(x,y); }

    // endregion
}
