package poc;

import be.kdg.integration2.mvpglobal.model.dataobjects.PositionData;
import be.kdg.integration2.mvpglobal.model.pieces.Piece;

public class Move {
    private Player player;
    private Piece piece;
    private PositionData position;

    private long startTime;
    private long endTime;

    public Move(Player player, Piece piece, PositionData position) {
        this.player = player;
        this.piece = piece;
        this.position = position;
        this.startTime = System.currentTimeMillis();
    }

    public long getTime() { return endTime - startTime; }

    public Player getPlayer() { return player; }
    public Piece getPiece() { return piece; }
    public PositionData getPosition() { return position; }

    public void setPlayer(Player player) { this.player = player; }
    public void setEndTime(long endTime) { this.endTime = endTime; }
}
