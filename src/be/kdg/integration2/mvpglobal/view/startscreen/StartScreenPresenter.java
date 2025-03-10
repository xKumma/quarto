package be.kdg.integration2.mvpglobal.view.startscreen;

import be.kdg.integration2.mvpglobal.model.*;
import be.kdg.integration2.mvpglobal.view.*;
import be.kdg.integration2.mvpglobal.view.loginscreen.LoginScreenPresenter;
import be.kdg.integration2.mvpglobal.view.loginscreen.LoginScreenView;
import be.kdg.integration2.mvpglobal.view.mainscreen.MainScreenPresenter;
import be.kdg.integration2.mvpglobal.view.mainscreen.MainScreenView;
import javafx.event.*;
import javafx.scene.control.Alert;
import javafx.stage.WindowEvent;
import java.net.MalformedURLException;

public class StartScreenPresenter {

    private MVPModel model;
    private StartScreenView view;
    private UISettings uiSettings;

    public StartScreenPresenter(MVPModel model, StartScreenView view, UISettings uiSettings) {
        this.model = model;
        this.view = view;
        this.uiSettings = uiSettings;
        updateView();
        EventHandlers();
    }

    private void updateView() {
        view.getTitolo().getStylesheets().add(uiSettings.getStyleSheetPath().toString());
    }

    private void EventHandlers() {
        view.getTransition().setOnFinished(event -> {
            LoginScreenView loginView = new LoginScreenView(uiSettings);
            LoginScreenPresenter loginPresenter = new LoginScreenPresenter(model, loginView,uiSettings);
            view.getScene().setRoot(loginView);
            loginView.getScene().getWindow().setWidth(600);
            loginView.getScene().getWindow().setHeight(600);
        });
    }
/* Needed?
    public void windowsHandler() {
        view.getScene().getWindow().setOnCloseRequest(event -> {
                 final Alert stopWindow = new Alert(Alert.AlertType.ERROR);
                 stopWindow.setHeaderText("You can not yet close the application.");
                 stopWindow.setContentText("Try again after the program has started");
                 stopWindow.showAndWait();
                 event.consume(); } );
    }

 */



}
