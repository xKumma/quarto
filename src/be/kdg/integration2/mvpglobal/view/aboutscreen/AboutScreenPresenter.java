package be.kdg.integration2.mvpglobal.view.aboutscreen;

import be.kdg.integration2.mvpglobal.model.*;
import be.kdg.integration2.mvpglobal.utility.Router;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;

public class AboutScreenPresenter extends BasePresenter<AboutScreenView, BaseModel> {

    public AboutScreenPresenter(MVPModel model, AboutScreenView view) {
        super(view, model);
        addEventHandlers();
    }

    protected void addEventHandlers() {
        view.getBtnOk().setOnAction(event -> view.getScene().getWindow().hide());
        view.getMenuButton().setOnAction(e -> goToMenu());
    }

    protected void goToMenu() {
        Router.getInstance().goTo(Screen.MAIN_MENU);
    }
}
