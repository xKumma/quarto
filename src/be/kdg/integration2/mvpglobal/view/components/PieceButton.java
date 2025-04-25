package be.kdg.integration2.mvpglobal.view.components;

import javafx.scene.control.Button;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.InputStream;
import java.util.Objects;

public class PieceButton extends Button {
    private String pieceImagePath;
    private String color;
    public static final double BUTTON_SIZE = 72;
    private boolean active;

    public PieceButton(String pieceImagePath, String color) {
        super();
        this.pieceImagePath = pieceImagePath;
        this.color = color;
        setupComponent();
        active = true;
    }

    private void setupComponent() {
        setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
        setMinSize(BUTTON_SIZE, BUTTON_SIZE);
        setMaxSize(BUTTON_SIZE, BUTTON_SIZE);

        getStyleClass().add("piece-button");

        updateImage();
    }

    private void updateImage() {
        if (pieceImagePath == null || color == null || color.isEmpty() || pieceImagePath.isEmpty()) {
            setGraphic(null);
            active = false;
            System.out.println("Deactivated " + GridPane.getColumnIndex(this) + " " + GridPane.getRowIndex(this));
            return;
        }

        InputStream stream = Objects.requireNonNull(getClass().getResourceAsStream(pieceImagePath));
        Image image = new Image(stream);

        ImageView imageView = new ImageView(image);

        Lighting lighting = new Lighting();
        lighting.setLight(new Light.Distant(45, 45, Color.web(color)));

        imageView.setEffect(lighting);

        setGraphic(imageView);
    }

    @Override
    public String toString() {
        if (pieceImagePath != null) {
            String[] parts = pieceImagePath.split("/");
            return parts[parts.length - 1].replaceAll(".png","") + "#" +color;
        }
        return super.toString();
    }

    public void setPieceImage(String newImagePath, String newColor) {
        this.pieceImagePath = newImagePath != null ?  "/images/pieces/" + newImagePath + ".png" : null;
        this.color = newColor;

        updateImage();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
