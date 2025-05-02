package be.kdg.integration2.mvpglobal.view.statscreen;

import be.kdg.integration2.mvpglobal.model.BaseModel;
import be.kdg.integration2.mvpglobal.utility.Router;
import be.kdg.integration2.mvpglobal.model.Screen;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;

public class TablePresenter extends BasePresenter<TabletView, BaseModel> {

    public TablePresenter(BaseModel model, TabletView tableView) {
        super(tableView, model);
    }

    protected void addEventHandlers() {

        view.getMenuButton().setOnAction(e -> goToMenu());
        view.getBackButton().setOnAction(e -> goToWin());
    }

    private void goToMenu() {
        Router.getInstance().goTo(Screen.MAIN_MENU, null);
    }

    private void goToWin() { Router.getInstance().goTo(Screen.END_SCREEN, null); }
}
