package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.model.dataobjects.PositionData;
import be.kdg.integration2.mvpglobal.model.pieces.Piece;

// temporarily copied to model for testing and to prevent errors. Will be fully moved from poc to model
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

    public Move(Piece selectedPiece) {
        this.piece = selectedPiece;
        this.startTime = System.currentTimeMillis();
    }

    public long getTime() { return endTime - startTime; }
    public long getStartTime() { return startTime; }

    public String getPlayer() { return player; }
    public Piece getPiece() { return piece; }

    public PositionData getPosition() { return position; }

    public void setPlayer(Player player) { this.player = player.toString(); }
    public void setEndTime(long endTime) { this.endTime = endTime; }

    public void setPiece(Piece piece) { this.piece = piece;
    }

    public void setPosition(int x, int y) {
        position = new PositionData(x,y);
    }

    public String toJson() {
        return String.format("{\"player\":%s,\"x\":%d,\"y\":%d,\"pieceSlug\":%s,\"startTime\":%d, \"endTime\":%d}",
                player, position.x(), position.y(), piece.toString(), startTime, endTime);
    }

}
