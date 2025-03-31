package be.kdg.integration2.mvpglobal.view.startscreen;

import be.kdg.integration2.mvpglobal.model.BaseModel;
import be.kdg.integration2.mvpglobal.model.Router;
import be.kdg.integration2.mvpglobal.model.Screen;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;

public final class StartScreenPresenter extends BasePresenter<StartScreenView, BaseModel> {

    public StartScreenPresenter(StartScreenView view, BaseModel model) {
        super(view, model);
        updateView();
    }

    protected void updateView() {
        view.getTitolo().getStylesheets().add(uiSettings.getStyleSheetPath().toString());
    }

    protected void addEventHandlers() {
        view.getTransition().setOnFinished(event -> {
            Router.getInstance().goTo(Screen.LOGIN);
        });

        // Skip the login screen for quicker testing
        //view.getTransition().setOnFinished(event -> {
        //    Router.getInstance().mainScreen();
        //});
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
