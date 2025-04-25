package be.kdg.integration2.mvpglobal.model.pieces;

import java.util.HashMap;
import java.util.Map;

public class Piece {
    private final PieceType type;
    private final PieceColor color;
    private final PieceShape shape;
    private final PieceSize size;

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
        Map<PieceAttribute, Object> attributes = new HashMap<>();
        attributes.put(PieceAttribute.TYPE, type);
        attributes.put(PieceAttribute.COLOR, color);
        attributes.put(PieceAttribute.SHAPE, shape);
        attributes.put(PieceAttribute.SIZE, size);
        return attributes;
    }

    public String getOneAttribute(PieceAttribute attribute) {
        if (attribute == PieceAttribute.TYPE) {
            return type.toString();
        } else if (attribute == PieceAttribute.COLOR) {
            return color.toString();
        } else if (attribute == PieceAttribute.SHAPE) {
            return shape.toString();
        } else {
            return size.toString();
        }
    }

    @Override
    public String toString() {
        return String.format(
                "%s_%s_%s#%s", shape.toString(), size.toString(), type.toString(), color.toString()
        ).toLowerCase();
    }
}
