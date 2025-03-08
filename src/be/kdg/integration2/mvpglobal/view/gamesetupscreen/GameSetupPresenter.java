package be.kdg.integration2.mvpglobal.view.gamesetupscreen;

import be.kdg.integration2.mvpglobal.model.MVPModel;
import be.kdg.integration2.mvpglobal.view.UISettings;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;

public class GameSetupPresenter extends BasePresenter<GameSetupScreen, MVPModel> {
    public GameSetupPresenter(GameSetupScreen view, MVPModel model, UISettings uiSettings) {
        super(view, model, uiSettings);
    }
}
