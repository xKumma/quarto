package be.kdg.integration2.mvpglobal.model.dataobjects;

public record BoardUpdateData(String imagePath, String color, int x, int y) {
    public BoardUpdateData(String slug, int x, int y) {
        this(
                (slug != null && slug.contains("#")) ? slug.split("#")[0] : null,
                (slug != null && slug.contains("#")) ? slug.split("#")[1] : null,
                x, y
        );
    }

    public String getFullSlug() {
        return (imagePath != null && color != null) ? imagePath + "#" + color : null;
    }
}
