package be.kdg.integration2.mvpglobal.view.mainmenu;

import be.kdg.integration2.mvpglobal.model.BaseModel;
import be.kdg.integration2.mvpglobal.model.MVPModel;
import be.kdg.integration2.mvpglobal.model.Screen;
import be.kdg.integration2.mvpglobal.utility.Router;
import be.kdg.integration2.mvpglobal.utility.dbconnection.DBManager;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;

/**
 * Presenter class for the MainMenu view.
 * <p>
 * This class handles the logic and interactions for the MainMenu view,
 * including navigation to other screens and disabling buttons based on database connection status.
 */
public class MainMenuPresenter extends BasePresenter<MainMenuView, BaseModel> {
    public MainMenuPresenter(MainMenuView view, MVPModel model) {
        super(view, model);
        if (!DBManager.getInstance().isConnected()) {
            view.getLeaderboardButton().setDisable(true);
            /*
            // test buttons
             view.getStatisticsButton().setDisable(true);
             view.getTableButton().setDisable(true);
            */
        }
    }

    protected void addEventHandlers() {
        view.getQuitButton().setOnAction(e -> quitGame());
        view.getLeaderboardButton().setOnAction(e ->  goToLeaderboard());
        view.getRulesButton().setOnAction(e -> goToRules());
        view.getStartGameButton().setOnAction(e -> goToGameSetup());
        /* --- buttons for testing ---
        view.getTableButton().setOnAction(e -> goToTable());
        view.getStatisticsButton().setOnAction(e -> goToStatistics());
        view.getAboutButton().setOnAction(e -> goToAbout());
        view.getSettingsButton().setOnAction(e -> goToSettings());

         */

    }

    private void goToAbout() {
        Router.getInstance().goTo(Screen.ABOUT);
    }

    private void goToSettings() {
        Router.getInstance().goTo(Screen.SETTINGS);
    }

    private void quitGame() {
        view.getScene().getWindow().hide();
    }

    private void goToTable() {
        Router.getInstance().goTo(Screen.TABLE);
    }

    private void goToStatistics() {
        Router.getInstance().goTo(Screen.END_SCREEN);
    }


    private void goToLeaderboard() {
        Router.getInstance().goTo(Screen.LEADERBOARD);

    }
    private void goToRules() {
        Router.getInstance().goTo(Screen.RULES);
    }

    private void goToGameSetup() {
        Router.getInstance().goTo(Screen.GAME_SETUP);

    }




}
