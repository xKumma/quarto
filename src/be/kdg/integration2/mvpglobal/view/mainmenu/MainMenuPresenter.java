package be.kdg.integration2.mvpglobal.view.mainmenu;

import be.kdg.integration2.mvpglobal.model.BaseModel;
import be.kdg.integration2.mvpglobal.model.MVPModel;
import be.kdg.integration2.mvpglobal.model.Router;
import be.kdg.integration2.mvpglobal.model.Screen;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;

public class MainMenuPresenter extends BasePresenter<MainMenuView, BaseModel> {
    public MainMenuPresenter(MainMenuView view, MVPModel model) {
        super(view, model);
    }

    protected void addEventHandlers() {
        view.getQuitButton().setOnAction(e -> quitGame());
        view.getLeaderboardButton().setOnAction(e ->  goToLeaderboard());
        view.getRulesButton().setOnAction(e -> goToRules());
        view.getStartGameButton().setOnAction(e -> goToGameSetup());
        view.getTableButton().setOnAction(e -> goToTable());
        view.getStatisticsButton().setOnAction(e -> goToStatistics());
    }

    private void quitGame() {
        //quit
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
