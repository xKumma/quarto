package be.kdg.integration2.mvpglobal.view.leaderboardscreen;

import be.kdg.integration2.mvpglobal.dbconnection.DBManager;
import be.kdg.integration2.mvpglobal.model.BaseModel;
import be.kdg.integration2.mvpglobal.model.Router;
import be.kdg.integration2.mvpglobal.model.Screen;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;

public class LeaderboardScreenPresenter extends BasePresenter<LeaderboardScreenView, BaseModel> {

    public LeaderboardScreenPresenter(LeaderboardScreenView view, BaseModel model) {
        super(view, model);
    }

    protected void addEventHandlers() {
        view.getMainButton().setOnAction(Event -> {
            Router.getInstance().goTo(Screen.MAIN_MENU);
        });

        view.getAscMI().setOnAction(event -> {
            DBManager.getInstance().fillLeaderboard("player_username");
        });

        view.getDescMI().setOnAction(event -> {
            DBManager.getInstance().fillLeaderboard("ASC");
        });

        view.getWinsMI().setOnAction(event -> {
            DBManager.getInstance().fillLeaderboard("ASC");
        });

        view.getLossMI().setOnAction(event -> {
            DBManager.getInstance().fillLeaderboard("ASC");
        });

        view.getAvgTMI().setOnAction(event -> {
            DBManager.getInstance().fillLeaderboard("ASC");
        });

    }
}
