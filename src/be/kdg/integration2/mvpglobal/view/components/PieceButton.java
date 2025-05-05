package be.kdg.integration2.mvpglobal.view.components;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.InputStream;
import java.util.Objects;

/**
 * A custom button component representing a game piece.
 * It includes layout configurations and functionality for setting an image.
 */
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

    /**
     * Updates the button's graphic with the piece image and applies a lighting effect.
     */
    private void updateImage() {
        if (pieceImagePath == null || color == null || color.isEmpty() || pieceImagePath.isEmpty()) {
            setGraphic(null);
            active = false;
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

    /**
     * Plays a flash animation on the button's graphic.
     * @param onFinished A callback to be executed after the animation finishes.
     */
    public void playFlashAnimation(Runnable onFinished) {
        Node graphic = getGraphic();
        if (graphic == null) return;

        // Simple scale + fade combo
        ScaleTransition scale = new ScaleTransition(Duration.millis(150), graphic);
        scale.setToX(1.2);
        scale.setToY(1.2);
        scale.setAutoReverse(true);
        scale.setCycleCount(8);

        FadeTransition fade = new FadeTransition(Duration.millis(150), graphic);
        fade.setFromValue(1.0);
        fade.setToValue(0.3);
        fade.setAutoReverse(true);
        fade.setCycleCount(8);

        ParallelTransition flash = new ParallelTransition(scale, fade);
        flash.setOnFinished(e -> {
            graphic.setOpacity(1.0); // restore full opacity
            graphic.setScaleX(1.0);  // restore original scale
            graphic.setScaleY(1.0);
            if (onFinished != null) onFinished.run();
        });

        flash.play();
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
