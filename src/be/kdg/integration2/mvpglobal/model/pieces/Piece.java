package be.kdg.integration2.mvpglobal.model.pieces;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a game piece with specific attributes (type, color, shape, and size).
 */
public class Piece {
    private final PieceType type;
    private final PieceColor color;
    private final PieceShape shape;
    private final PieceSize size;

    /**
     * Constructs a `Piece` object with the specified attributes.
     *
     * @param type  The type of the piece (full/hollow).
     * @param color The color of the piece (blue/red).
     * @param shape The shape of the piece (square/circle).
     * @param size  The size of the piece (big/small).
     */
    public Piece(PieceType type,
                 PieceColor color,
                 PieceShape shape,
                 PieceSize size) {
        this.type = type;
        this.color = color;
        this.shape = shape;
        this.size = size;
    }

    /**
     * Constructs a `Piece` object from a slug string.<br>
     * The slug is expected to have the format: "shape_size_type#color".
     *
     * @param slug A string representing the piece attributes,
     *             where the part before `#` specifies shape, size, and type
     *             separated by underscores, and the part after `#` specifies the color.
     */
    public Piece(String slug) {
        String[] parts = slug.split("#");
        String[] attrs = parts[0].split("_");

        shape = PieceShape.valueOf(attrs[0].toUpperCase());
        size = PieceSize.valueOf(attrs[1].toUpperCase());
        type = PieceType.valueOf(attrs[2].toUpperCase());
        color = PieceColor.valueOf(parts[1].toUpperCase());
    }

    /**
     * Retrieves the attributes of the piece as a map.<br>
     * The map contains the following key-value pairs:<br>
     * - <code>PieceAttribute.TYPE</code>: The type of the piece.<br>
     * - <code>PieceAttribute.COLOR</code>: The color of the piece.<br>
     * - <code>PieceAttribute.SHAPE</code>: The shape of the piece.<br>
     * - <code>PieceAttribute.SIZE</code>: The size of the piece.
     *
     * @return A map where the keys are `PieceAttribute` enums and the values are the corresponding attributes of the piece.
     */
    public Map<PieceAttribute, Object> getAttributes() {
        Map<PieceAttribute, Object> attributes = new HashMap<>();
        attributes.put(PieceAttribute.TYPE, type);
        attributes.put(PieceAttribute.COLOR, color);
        attributes.put(PieceAttribute.SHAPE, shape);
        attributes.put(PieceAttribute.SIZE, size);
        return attributes;
    }

    @Override
    public String toString() {
        return String.format(
                "%s_%s_%s#%s", shape.toString(), size.toString(), type.toString(), color.toString()
        ).toLowerCase();
    }
}
