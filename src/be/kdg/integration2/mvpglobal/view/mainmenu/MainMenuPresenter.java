package be.kdg.integration2.mvpglobal.view.mainmenu;

import be.kdg.integration2.mvpglobal.model.MVPModel;
import be.kdg.integration2.mvpglobal.view.UISettings;
import be.kdg.integration2.mvpglobal.view.gamescreen.GameScreenPresenter;
import be.kdg.integration2.mvpglobal.view.gamescreen.GameScreenView;
import be.kdg.integration2.mvpglobal.view.gamescreen.TestModel;
import be.kdg.integration2.mvpglobal.view.gamesetupscreen.GameSetupView;
import be.kdg.integration2.mvpglobal.view.rules.RulesPresenter;
import be.kdg.integration2.mvpglobal.view.rules.RulesView;
import be.kdg.integration2.mvpglobal.view.startscreen.StartScreenView;
import javafx.scene.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

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
        //quit
    }
    private void goToLeaderboard() {
        //goTo(Screen.LEADERBOARD, null);

    }
    private void goToRules() {
        //goTo(Screen.RULES, null);
    }

    private void goToGameSetup() {
        //goTo(Screen.GAMESETUP, null);

    }




}
