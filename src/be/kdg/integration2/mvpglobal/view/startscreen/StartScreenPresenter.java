package be.kdg.integration2.mvpglobal.view.startscreen;

import be.kdg.integration2.mvpglobal.model.BaseModel;
import be.kdg.integration2.mvpglobal.model.MVPModel;
import be.kdg.integration2.mvpglobal.view.UISettings;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;
import be.kdg.integration2.mvpglobal.view.base.BaseView;
import be.kdg.integration2.mvpglobal.view.loginscreen.LoginScreenPresenter;
import be.kdg.integration2.mvpglobal.view.loginscreen.LoginScreenView;

public final class StartScreenPresenter extends BasePresenter<StartScreenView, BaseModel> {

    public StartScreenPresenter(BaseModel model, BaseView view, UISettings uiSettings) {
        super((StartScreenView) view, model, uiSettings);
        updateView();
        EventHandlers();
    }

    protected void updateView() {
        view.getTitolo().getStylesheets().add(uiSettings.getStyleSheetPath().toString());
    }

    private void EventHandlers() {
        view.getTransition().setOnFinished(event -> {
            LoginScreenView loginView = new LoginScreenView(uiSettings);
            LoginScreenPresenter loginPresenter = new LoginScreenPresenter((MVPModel) model, loginView,uiSettings);
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
