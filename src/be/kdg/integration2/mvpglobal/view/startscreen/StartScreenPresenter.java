package be.kdg.integration2.mvpglobal.view.startscreen;

import be.kdg.integration2.mvpglobal.dbconnection.DBManager;
import be.kdg.integration2.mvpglobal.model.BaseModel;
import be.kdg.integration2.mvpglobal.model.MVPModel;
import be.kdg.integration2.mvpglobal.model.Router;
import be.kdg.integration2.mvpglobal.model.Screen;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;
import be.kdg.integration2.mvpglobal.view.loginscreen.LoginScreenPresenter;
import be.kdg.integration2.mvpglobal.view.loginscreen.LoginScreenView;

import java.sql.SQLException;

public final class StartScreenPresenter extends BasePresenter<StartScreenView, BaseModel> {

    public StartScreenPresenter(StartScreenView view, BaseModel model) {
        super(view, model);
        updateView();
    }

    protected void updateView() {
    }

    protected void addEventHandlers() {
        view.getTransition().setOnFinished(event -> {
            Router.getInstance().goTo(Screen.LOGIN);

            try {
                DBManager.setupDatabase();
            } catch (SQLException e) {
               System.out.println("server not available");
            }

        });

        // Skip the login screen for quicker testing
        //view.getTransition().setOnFinished(event -> {
        //    Router.getInstance().mainScreen();
        //});
    }


}
