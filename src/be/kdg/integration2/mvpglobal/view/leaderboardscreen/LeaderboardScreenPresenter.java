package be.kdg.integration2.mvpglobal.view.leaderboardscreen;

import be.kdg.integration2.mvpglobal.utility.dbconnection.DBManager;
import be.kdg.integration2.mvpglobal.model.BaseModel;
import be.kdg.integration2.mvpglobal.utility.Router;
import be.kdg.integration2.mvpglobal.model.Screen;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;

public class LeaderboardScreenPresenter extends BasePresenter<LeaderboardScreenView, BaseModel> {

    public LeaderboardScreenPresenter(LeaderboardScreenView view, BaseModel model) {
        super(view, model);
     //   DBManager.fillLeaderboard();
    }

    protected void addEventHandlers() {

        view.getMainButton().setOnAction(Event -> {
            Router.getInstance().goTo(Screen.MAIN_MENU);
        });
/*
        view.getAscMI().setOnAction(event -> {
            DBManager.fillLeaderboard();
        });

        view.getDescMI().setOnAction(event -> {
            DBManager.fillLeaderboard();
        });

        view.getWinsMI().setOnAction(event -> {
            DBManager.fillLeaderboard();
        });

        view.getLossMI().setOnAction(event -> {
            DBManager.fillLeaderboard();
        });

        view.getAvgTMI().setOnAction(event -> {
            DBManager.fillLeaderboard();
        });


 */
    }
}
