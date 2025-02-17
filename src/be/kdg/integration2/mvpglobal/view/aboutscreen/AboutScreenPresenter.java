package be.kdg.integration2.mvpglobal.view.aboutscreen;

import be.kdg.integration2.mvpglobal.model.*;
import be.kdg.integration2.mvpglobal.view.UISettings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class AboutScreenPresenter {

    private MVPModel model;
    private AboutScreenView view;
    private UISettings uiSettings;

    public AboutScreenPresenter(MVPModel model, AboutScreenView view, UISettings uiSettings) {
        this.model = model;
        this.view = view;
        this.uiSettings = uiSettings;
        view.getBtnOk().setOnAction(event -> view.getScene().getWindow().hide());
    }
}
