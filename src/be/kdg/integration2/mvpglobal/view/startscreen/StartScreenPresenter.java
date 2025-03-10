package be.kdg.integration2.mvpglobal.view.startscreen;

import be.kdg.integration2.mvpglobal.model.*;
import be.kdg.integration2.mvpglobal.view.*;
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
            msPresenter.windowsHandler();
        });
    }

    public void windowsHandler() {
        view.getScene().getWindow().setOnCloseRequest(event -> {
                 final Alert stopWindow = new Alert(Alert.AlertType.ERROR);
                 stopWindow.setHeaderText("You can not yet close the application.");
                 stopWindow.setContentText("Try again after the program has started");
                 stopWindow.showAndWait();
                 event.consume(); } );
    }



}
