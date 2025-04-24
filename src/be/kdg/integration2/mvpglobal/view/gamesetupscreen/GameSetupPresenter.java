package be.kdg.integration2.mvpglobal.view.gamesetupscreen;

import be.kdg.integration2.mvpglobal.model.BotDifficulty;
import be.kdg.integration2.mvpglobal.model.GameSetup;
import be.kdg.integration2.mvpglobal.model.Screen;
import be.kdg.integration2.mvpglobal.model.dataobjects.GameSessionData;
import be.kdg.integration2.mvpglobal.utility.Router;
import be.kdg.integration2.mvpglobal.utility.SaveManager;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;
import javafx.scene.control.ToggleButton;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class GameSetupPresenter extends BasePresenter<GameSetupView, GameSetup> {

    public GameSetupPresenter(GameSetupView view, GameSetup model) {
        super(view, model);
    }

    @Override
    protected void addEventHandlers() {
        super.addEventHandlers();

        List<ToggleButton> startingPlayerButtons = view.getStartingPlayerButtons();

        for (int i = 0; i < startingPlayerButtons.size(); i++) {
            int index = i;
            startingPlayerButtons.get(i).setOnAction(e -> {
                model.setStartingPlayer(index);
            });
        }

        List<ToggleButton> difficultyButtons = view.getDifficultyButtons();
        for (int i = 0; i < difficultyButtons.size(); i++) {
            BotDifficulty difficulty = BotDifficulty.values()[i];
            difficultyButtons.get(i).setOnAction(e -> {
                model.setDifficulty(difficulty);
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
    }

    private void goToMenu() {
        Router.Instance.goTo(Screen.MAIN_MENU);
    }

    private void startGame() {
        Router.Instance.goTo(Screen.GAME, model.getSessionData());
    }
}
