package be.kdg.integration2.mvpglobal.model.dataobjects;

/**
 * A data object representing an update to a board with a piece.
 *
 * @param imageName The name of the image associated with the piece.
 * @param color The color associated with the piece.
 * @param x The x-coordinate of the piece.
 * @param y The y-coordinate of the piece.
 */
public record BoardUpdateData(String imageName, String color, int x, int y) {
    public BoardUpdateData(String slug, int x, int y) {
        this(
                (slug != null && slug.contains("#")) ? slug.split("#")[0] : null,
                (slug != null && slug.contains("#")) ? slug.split("#")[1] : null,
                x, y
        );
    }
}
