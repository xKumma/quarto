package be.kdg.integration2.mvpglobal.view.mainmenu;

import be.kdg.integration2.mvpglobal.model.MVPModel;
import be.kdg.integration2.mvpglobal.view.UISettings;
import be.kdg.integration2.mvpglobal.view.gamesetupscreen.GameSetupView;
import be.kdg.integration2.mvpglobal.view.startscreen.StartScreenView;
import javafx.scene.*;

public class MainMenuPresenter {

    private MVPModel model;
    private MainMenuView view;
    private UISettings uiSettings;

    public MainMenuPresenter(MainMenuView view, MVPModel model, UISettings uiSettings) {
        this.model = model;
        this.view = view;
        this.uiSettings = uiSettings;
    }

    protected void addEventHandlers() {
        view.getQuitButton().setOnAction(e -> quitGame());
        view.getLeaderboardButton().setOnAction(e ->  goToLeaderboard());
        view.getRulesButton().setOnAction(e -> goToRules());
        view.getStartGameButton().setOnAction(e -> goToGameSetup());
    }

    private void quitGame() {

    }
    private void goToLeaderboard() {

    }
    private void goToRules() {

    }
    private void goToGameSetup() {

    }




}
