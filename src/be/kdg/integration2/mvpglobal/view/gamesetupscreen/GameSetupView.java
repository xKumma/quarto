package be.kdg.integration2.mvpglobal.view.gamesetupscreen;

import be.kdg.integration2.mvpglobal.view.base.BaseView;
import be.kdg.integration2.mvpglobal.view.components.Header;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * GameSetupView class represents the game setup screen of the application.
 */
public class GameSetupView extends BaseView {

    private List<ToggleButton> startingPlayerButtons;

    private Button startBtn;
    private Button loadFileBtn;
    private Button menuBtn;

    public GameSetupView() {
        super();
    }

    @Override
    protected void initialiseNodes() {
        startingPlayerButtons = new ArrayList<>(
                Arrays.asList(
                    new ToggleButton("Random Start"),
                    new ToggleButton("Player Starts"),
                    new ToggleButton("Bot Starts")
                )
        );
        ToggleGroup playerGroup;

        playerGroup = new ToggleGroup();
        for (ToggleButton button : startingPlayerButtons) {
            button.setToggleGroup(playerGroup);
        }
        playerGroup.selectToggle(startingPlayerButtons.getFirst());

        startBtn = new Button("Start");
        loadFileBtn = new Button("Load Game");
        menuBtn = new Button("Menu");
    }

    @Override
    protected void layoutNodes() {
        //setTop(new Header());
        loadFileBtn.getStyleClass().addAll("menu-button", "small");

        startingPlayerButtons.forEach(node -> {
            node.getStyleClass().add("menu-button");
            node.setPrefSize(175, 40);
        });

        VBox startingPlayerOptions = new VBox(loadFileBtn, new Label("Starting Player"));
        startingPlayerOptions.getStyleClass().addAll("black");
        startingPlayerOptions.setStyle(
                "-fx-font-size: 16px"
        );

        startingPlayerOptions.getChildren().addAll(startingPlayerButtons);
        startingPlayerOptions.setSpacing(15);
        startingPlayerOptions.setAlignment(Pos.CENTER);

        Header header = new Header();
        header.setStyle(
                "-fx-padding: 0px; "
        );
        VBox centerOptions = new VBox(header, startingPlayerOptions);
        centerOptions.getStyleClass().addAll("setup-container");
        centerOptions.setSpacing(24);
        centerOptions.setAlignment(Pos.CENTER);

        centerOptions.setMaxSize(400, 400);

        HBox bottomContainer = new HBox(startBtn,menuBtn);
        bottomContainer.getChildren().forEach(node -> {
            ((Button)node).setPrefSize(115, 40);
            node.getStyleClass().addAll("menu-button", "small", "white");
        });
        bottomContainer.setAlignment(Pos.BOTTOM_CENTER);
        bottomContainer.setSpacing(64);
        bottomContainer.setPadding(new Insets(-0, 0, 24, 0));
        //setBottom(bottomContainer);

        Rectangle rectangle = new Rectangle();
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(3);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setRotate(45);
        rectangle.setMouseTransparent(true);

        rectangle.widthProperty().bind(centerOptions.heightProperty().add(60));
        rectangle.heightProperty().bind(centerOptions.heightProperty().add(60));

        StackPane root = new StackPane(bottomContainer, rectangle, centerOptions);
        setCenter(root);
    }

    //region Getters
    List<ToggleButton> getStartingPlayerButtons() {
        return startingPlayerButtons;
    }

    Button getStartBtn() {
        return startBtn;
    }

    Button getLoadFileBtn() {
        return loadFileBtn;
    }

    Button getMenuBtn() {
        return menuBtn;
    }
    //endregion
}
