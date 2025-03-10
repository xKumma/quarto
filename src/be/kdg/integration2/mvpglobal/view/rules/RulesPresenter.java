package be.kdg.integration2.mvpglobal.view.rules;

import be.kdg.integration2.mvpglobal.model.MVPModel;
import be.kdg.integration2.mvpglobal.view.UISettings;
import be.kdg.integration2.mvpglobal.view.mainmenu.MainMenuView;

public class RulesPresenter {


    private MVPModel model;
    private RulesView view;
    private UISettings uiSettings;

    public RulesPresenter(MVPModel model, RulesView view, UISettings uiSettings) {
        this.model = model;
        this.view = view;
        this.uiSettings = uiSettings;
    }

    protected void addEventHandler() {
        view.getMenuButton().setOnAction(e -> goToMenu());
    }

    private void goToMenu() {

    }
}
