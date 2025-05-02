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
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameSetupView extends BaseView {

    private List<ToggleButton> startingPlayerButtons;
    private List<ToggleButton> difficultyButtons;

    private Button startBtn;
    private Button loadFileBtn;
    private Button loadDbBtn;
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
        ToggleGroup difficultyGroup;

        playerGroup = new ToggleGroup();
        for (ToggleButton button : startingPlayerButtons) {
            button.setToggleGroup(playerGroup);
        }
        playerGroup.selectToggle(startingPlayerButtons.getFirst());

        difficultyButtons = new ArrayList<>(
                Arrays.asList(
                    new ToggleButton("Easy"),
                    new ToggleButton("Medium"),
                    new ToggleButton("Hard")
                )
        );

        difficultyGroup = new ToggleGroup();
        for (ToggleButton button : difficultyButtons) {
            button.setToggleGroup(difficultyGroup);
        }
        difficultyGroup.selectToggle(difficultyButtons.getFirst());

        startBtn = new Button("Start");
        loadFileBtn = new Button("Load from file");
        loadDbBtn = new Button("Load last from Database");
        menuBtn = new Button("Menu");
    }

    @Override
    protected void layoutNodes() {
        setTop(new Header());

        HBox loadButtons = new HBox(loadFileBtn, loadDbBtn);
        loadButtons.setAlignment(Pos.CENTER);
        loadButtons.setSpacing(15);
        loadButtons.setPadding(new Insets(15));

        VBox startingPlayerOptions = new VBox(new Label("Starting Player"));
        startingPlayerOptions.getChildren().addAll(startingPlayerButtons);
        startingPlayerOptions.setSpacing(15);
        //startingPlayerOptions.setPadding(new Insets(15));

        VBox difficultyOptions = new VBox(new Label("Difficulty"));
        difficultyOptions.getChildren().addAll(difficultyButtons);
        difficultyOptions.setSpacing(15);

        HBox setupOptions = new HBox(startingPlayerOptions, difficultyOptions);
        setupOptions.setSpacing(32);
        setupOptions.setAlignment(Pos.CENTER);
        setupOptions.getStyleClass().addAll("setup-options");

        VBox centerOptions = new VBox(loadButtons,setupOptions);
        setCenter(centerOptions);
        centerOptions.getStyleClass().addAll("setup-container");
        centerOptions.setSpacing(32);

        HBox bottomContainer = new HBox(startBtn,menuBtn);
        bottomContainer.getStyleClass().add("container");
        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.setSpacing(15);
        bottomContainer.setPadding(new Insets(15));

        setBottom(bottomContainer);
    }

    //region Getters
    List<ToggleButton> getDifficultyButtons() {
        return difficultyButtons;
    }

    List<ToggleButton> getStartingPlayerButtons() {
        return startingPlayerButtons;
    }

    Button getStartBtn() {
        return startBtn;
    }

    Button getLoadFileBtn() {
        return loadFileBtn;
    }

    Button getLoadDbBtn() {
        return loadDbBtn;
    }

    Button getMenuBtn() {
        return menuBtn;
    }
    //endregion
}
