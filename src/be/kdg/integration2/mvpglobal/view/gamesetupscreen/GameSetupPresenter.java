package be.kdg.integration2.mvpglobal.view.gamesetupscreen;

import be.kdg.integration2.mvpglobal.model.BaseModel;
import be.kdg.integration2.mvpglobal.model.BotDifficulty;
import be.kdg.integration2.mvpglobal.model.GameSetup;
import be.kdg.integration2.mvpglobal.model.dataobjects.GameSessionData;
import be.kdg.integration2.mvpglobal.view.UISettings;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;
import be.kdg.integration2.mvpglobal.view.base.BaseView;
import javafx.scene.control.ToggleButton;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class GameSetupPresenter extends BasePresenter<GameSetupView, GameSetup> {
    private int startingPlayer = 0; // 0 = R, 1 = P, 2 = B; can be made into an enum
    private BotDifficulty difficulty = BotDifficulty.EASY;

    public GameSetupPresenter(BaseView view, BaseModel model, UISettings uiSettings) {
        super((GameSetupView) view, (GameSetup) model, uiSettings);
    }

    @Override
    protected void addEventHandlers() {
        super.addEventHandlers();

        List<ToggleButton> startingPlayerButtons = view.getStartingPlayerButtons();

        for (int i = 0; i < startingPlayerButtons.size(); i++) {
            int index = i;
            startingPlayerButtons.get(i).setOnAction(e -> {
                startingPlayer = index;
            });
        }

        List<ToggleButton> difficultyButtons = view.getDifficultyButtons();
        for (int i = 0; i < difficultyButtons.size(); i++) {
            BotDifficulty difficulty = BotDifficulty.values()[i];
            difficultyButtons.get(i).setOnAction(e -> {
                this.difficulty = difficulty;
            });
        }

        view.getStartBtn().setOnAction(e -> startGame());
        view.getMenuBtn().setOnAction(e ->  goToMenu());
        view.getLoadFileBtn().setOnAction(e -> openFileChooser());
        view.getLoadDbBtn().setOnAction(e -> loadFromDB());
    }

    private void openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Game Save File");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Game Save Files", "*.json", "*.xml"));

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            System.out.println("Selected File: " + selectedFile.getAbsolutePath());
            loadFromFile(selectedFile);
        } else {
            System.out.println("No file selected.");
        }
    }

    private void loadFromFile(File file) {
        System.out.println("Loading game from: " + file.getAbsolutePath());
    }

    private void loadFromDB() {
        //
    }

    private void goToMenu() {
        //
    }

    private void startGame() {
        GameSessionData gameSessionData = new GameSessionData(startingPlayer, difficulty);
        System.out.println("Starting Game: " + startingPlayer + " : " + difficulty);
        model.startGame(gameSessionData);
    }
}
