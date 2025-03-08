package be.kdg.integration2.mvpglobal.view.components;

import javafx.scene.control.Button;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.InputStream;
import java.util.Objects;

public class PieceButton extends Button {
    private String pieceImagePath;
    private String color;
    public static final double BUTTON_SIZE = 72;

    public PieceButton(String pieceImagePath, String color) {
        this.pieceImagePath = pieceImagePath;
        this.color = color;
        setupButton();
    }

    private void setupButton() {
        setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
        setMinSize(BUTTON_SIZE, BUTTON_SIZE);
        setMaxSize(BUTTON_SIZE, BUTTON_SIZE);
        updateImage();
    }

    private void updateImage() {
        if (pieceImagePath == null || color == null || color.isEmpty() || pieceImagePath.isEmpty()) {
            System.out.println("pieceImagePath or color is null");
            setGraphic(null);
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

    public String getPieceImagePath() {
        return pieceImagePath;
    }

    public String getColor() {
        return color;
    }
}
