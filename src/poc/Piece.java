package poc;

import java.util.HashMap;
import java.util.Map;

public class Piece {
    private PieceType type;
    private PieceColor color;
    private PieceShape shape;
    private PieceSize size;

    public Piece(PieceType type,
                 PieceColor color,
                 PieceShape shape,
                 PieceSize size) {
        this.type = type;
        this.color = color;
        this.shape = shape;
        this.size = size;
    }

    public Map<PieceAttribute, Object> getAttributes() {
        Map<PieceAttribute, Object> attributes = new HashMap<PieceAttribute, Object>();
        attributes.put(PieceAttribute.TYPE, type);
        attributes.put(PieceAttribute.COLOR, color);
        attributes.put(PieceAttribute.SHAPE, shape);
        attributes.put(PieceAttribute.SIZE, size);
        return attributes;
    }
}
