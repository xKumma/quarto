package be.kdg.integration2.mvpglobal.view.startscreen;

import be.kdg.integration2.mvpglobal.model.BaseModel;
import be.kdg.integration2.mvpglobal.model.Screen;
import be.kdg.integration2.mvpglobal.utility.Router;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;
import javafx.stage.Stage;

/**
 * Presenter class for the StartScreen view.
 * <p>
 * This class handles the logic and interactions for the StartScreen view,
 * including navigation to other screens.
 */
public final class StartScreenPresenter extends BasePresenter<StartScreenView, BaseModel> {

    public StartScreenPresenter(StartScreenView view, BaseModel model) {
        super(view, model);
    }

    protected void addEventHandlers() {
        view.getTransition().setOnFinished(event -> {
            Router.getInstance().goTo(Screen.LOGIN);
        });



        // Skip the login screen for quicker testing
        //view.getTransition().setOnFinished(event -> {
        //    Router.getInstance().mainScreen();
        //});
    }
    protected void updateView(){
        Stage stage = (Stage) view.getScene().getWindow();
        //stage.setResizable(false);
    }

}
