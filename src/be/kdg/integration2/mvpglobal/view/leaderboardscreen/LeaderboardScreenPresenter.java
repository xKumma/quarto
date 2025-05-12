package be.kdg.integration2.mvpglobal.view.leaderboardscreen;

import be.kdg.integration2.mvpglobal.utility.dbconnection.DBManager;
import be.kdg.integration2.mvpglobal.model.BaseModel;
import be.kdg.integration2.mvpglobal.utility.Router;
import be.kdg.integration2.mvpglobal.model.Screen;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;

public class LeaderboardScreenPresenter extends BasePresenter<LeaderboardScreenView, BaseModel> {

    /**
     * When the leaderboard screen is opened, the table will be filled with data from the DB
     */
    public LeaderboardScreenPresenter(LeaderboardScreenView view, BaseModel model) {
        super(view, model);
        DBManager.getInstance().fillLeaderboard();
    }

    protected void addEventHandlers() {

        view.getMainButton().setOnAction(Event -> {
            Router.getInstance().goTo(Screen.MAIN_MENU);
        });

    }
}
