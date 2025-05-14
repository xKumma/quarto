package be.kdg.integration2.mvpglobal.view.endscreen;

import be.kdg.integration2.mvpglobal.model.BaseModel;
import be.kdg.integration2.mvpglobal.model.Screen;
import be.kdg.integration2.mvpglobal.model.dataobjects.GameSessionData;
import be.kdg.integration2.mvpglobal.utility.Router;
import be.kdg.integration2.mvpglobal.utility.dbconnection.DBManager;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;

public class EndScreenPresenter extends BasePresenter<EndScreenView, BaseModel> {


    public EndScreenPresenter(EndScreenView endScreenView, BaseModel model) {
        super(endScreenView, model);

        if (!DBManager.getInstance().isConnected()) {
            view.getGraphButton().setDisable(true);
            view.getTableButton().setDisable(true);
        }
    }

    @Override
    public void init(Object data) {
        super.init(data);

        try {
            view.getWinnerTextLabel().setText(DBManager.getInstance().getWinnerName() + " won!");
        } catch (Exception e) {
            if (data != null)
                view.getWinnerTextLabel().setText(
                        ((GameSessionData)data).getMoveHistory().getLast().getPlayer() + " won!");
        }
    }

    protected void addEventHandlers() {

        view.getMenuButton().setOnAction(e -> goToMenu());
        view.getTableButton().setOnAction(e -> goToTable());
        view.getGraphButton().setOnAction(e -> goToGraph());
    }

    private void goToMenu() {
        Router.getInstance().goTo(Screen.MAIN_MENU, null);
    }

    private void goToTable() {
        Router.getInstance().goTo(Screen.TABLE, null);
    }

    private void goToGraph() {
        Router.getInstance().goTo(Screen.GRAPH, null);
    }

}
