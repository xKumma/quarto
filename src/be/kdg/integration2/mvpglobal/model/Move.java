package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.model.dataobjects.PositionData;
import be.kdg.integration2.mvpglobal.model.pieces.Piece;

// temporary copied to model for testing and to prevent errors. Will be fully moved from poc to model
public class Move {
    private Player player;
    private Piece piece;
    private PositionData position;

    private long startTime;
    private long endTime;

    public Move(){}

    public Move(Player player, Piece piece, PositionData position) {
        this.player = player;
        this.piece = piece;
        this.position = position;
        this.startTime = System.currentTimeMillis();
    }

    public Move(Piece selectedPiece) {
        this.piece = selectedPiece;
        this.startTime = System.currentTimeMillis();
    }

    public long getTime() { return endTime - startTime; }
    public long getStartTime() { return startTime; }

    public Player getPlayer() { return player; }
    public Piece getPiece() { return piece; }

    public PositionData getPosition() { return position; }

    public void setPlayer(Player player) { this.player = player; }
    public void setEndTime(long endTime) { this.endTime = endTime; }

    public void setPiece(Piece piece) { this.piece = piece;
    }

    public void setPosition(int x, int y) {
        position = new PositionData(x,y);
    }
}
