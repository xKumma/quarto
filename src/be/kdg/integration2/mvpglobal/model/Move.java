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

    /**
     * Instantiates a new Move.
     */
    public Move(){}

    /**
     * Instantiates a new Move.
     *
     * @param player   the player
     * @param piece    the piece
     * @param position the position
     */
    public Move(Player player, Piece piece, PositionData position) {
        this.player = player.toString();
        this.piece = piece;
        this.position = position;
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Instantiates a new Move.
     *
     * @param player    the player
     * @param piece     the piece
     * @param position  the position
     * @param startTime the start time
     * @param endTime   the end time
     */
    public Move(String player, Piece piece, PositionData position, long startTime, long endTime) {
        this.player = player;
        this.piece = piece;
        this.position = position;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Instantiates a new Move.
     *
     * @param player        the player
     * @param selectedPiece the selected piece
     */
    public Move(String player, Piece selectedPiece) {
        this.player = player;
        this.piece = selectedPiece;
        this.startTime = System.currentTimeMillis();
    }

    /**
     * To json string.
     *
     * @return the string
     */
    public String toJson() {
        return String.format("{\"player\":%s,\"x\":%d,\"y\":%d,\"pieceSlug\":%s,\"startTime\":%d, \"endTime\":%d}",
                player, position.x(), position.y(), piece.toString(), startTime, endTime);
    }

    // region Getters and Setters
    /**
     * Gets time.
     *
     * @return the time
     */
    public long getTime() { return endTime - startTime; }

    /**
     * Gets start time.
     *
     * @return the start time
     */
    public long getStartTime() { return startTime; }

    /**
     * Gets end time.
     *
     * @return the end time
     */
    public long getEndTime() { return endTime;}

    /**
     * Gets player.
     *
     * @return the player
     */
    public String getPlayer() { return player; }

    /**
     * Gets piece.
     *
     * @return the piece
     */
    public Piece getPiece() { return piece; }

    /**
     * Gets position.
     *
     * @return the position
     */
    public PositionData getPosition() { return position; }

    /**
     * Sets player.
     *
     * @param player the player
     */
    public void setPlayer(Player player) { this.player = player.toString(); }

    /**
     * Sets end time.
     *
     * @param endTime the end time
     */
    public void setEndTime(long endTime) { this.endTime = endTime; }

    /**
     * Sets piece.
     *
     * @param piece the piece
     */
    public void setPiece(Piece piece) { this.piece = piece;
    }

    /**
     * Sets position.
     *
     * @param x the x
     * @param y the y
     */
    public void setPosition(int x, int y) { position = new PositionData(x,y); }

    // endregion
}
