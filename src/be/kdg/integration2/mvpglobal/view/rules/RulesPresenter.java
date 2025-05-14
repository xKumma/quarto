package be.kdg.integration2.mvpglobal.view.rules;

import be.kdg.integration2.mvpglobal.model.BaseModel;
import be.kdg.integration2.mvpglobal.utility.Router;
import be.kdg.integration2.mvpglobal.model.Screen;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;

/**
 * Presenter class for the Rules view.
 * <p>
 * This class handles the logic and interactions for the Rules view,
 * including navigation to other screens.
 */
public class RulesPresenter extends BasePresenter<RulesView, BaseModel> {

    public RulesPresenter(RulesView rulesView, BaseModel model) {
        super(rulesView, model);
    }

    protected void addEventHandlers() {
        view.getMenuButton().setOnAction(e -> goToMenu());
    }

    private void goToMenu() {
        Router.getInstance().goTo(Screen.MAIN_MENU, null);
    }
}
