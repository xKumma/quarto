package be.kdg.integration2.mvpglobal.view.leaderboardscreen;

import be.kdg.integration2.mvpglobal.model.MVPModel;
import be.kdg.integration2.mvpglobal.view.UISettings;
import be.kdg.integration2.mvpglobal.view.mainscreen.MainScreenPresenter;
import be.kdg.integration2.mvpglobal.view.mainscreen.MainScreenView;

import java.net.MalformedURLException;

public class LeaderboardScreenPresenter {
    private MVPModel model;
    private LeaderboardScreenView view;
    private UISettings uiSettings;

    public LeaderboardScreenPresenter(MVPModel model, LeaderboardScreenView view, UISettings uiSettings) {
        this.model = model;
        this.view = view;
        this.uiSettings = uiSettings;
        updateView();
        EventHandlers();
    }

    private void updateView() {
    }

    private void EventHandlers() {
        view.getMainButton().setOnAction(Event -> {
            MainScreenView msView = new MainScreenView(uiSettings);
            MainScreenPresenter msPresenter = new MainScreenPresenter(model, msView, uiSettings);
            view.getScene().setRoot(msView);
            try {
                msView.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
            } catch (MalformedURLException ex) {
                // // do nothing, if toURL-conversion fails, program can continue
            }
            msView.getScene().getWindow().sizeToScene();
            msView.getScene().getWindow().setX(uiSettings.getResX()/20);
            msView.getScene().getWindow().setY(uiSettings.getResY()/20);
            msView.getScene().getWindow().setHeight(9 * uiSettings.getResY()/10);
            msView.getScene().getWindow().setWidth(9 * uiSettings.getResX()/10);
        });
    }
}
