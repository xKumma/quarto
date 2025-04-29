package be.kdg.integration2.mvpglobal.view.loginscreen;

import be.kdg.integration2.mvpglobal.utility.dbconnection.DBManager;
import be.kdg.integration2.mvpglobal.model.BaseModel;
import be.kdg.integration2.mvpglobal.model.MVPModel;
import be.kdg.integration2.mvpglobal.utility.Router;
import be.kdg.integration2.mvpglobal.model.Screen;
import be.kdg.integration2.mvpglobal.view.UISettings;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;
import be.kdg.integration2.mvpglobal.view.mainmenu.MainMenuView;
import be.kdg.integration2.mvpglobal.view.statscreen.TabletView;
import javafx.scene.control.Alert;

public class LoginScreenPresenter extends BasePresenter<LoginScreenView, BaseModel> {

    public LoginScreenPresenter(MVPModel model, LoginScreenView view) {
        super(view, model);
        //updateView();
        //addEventHandlers();
    }

    protected void updateView() {}

    protected void addEventHandlers() {
        view.getLoginButton().setOnAction(event -> {
            // Does not do anything if TextFields are empty
            if(!contentChecker()){return;}
            if(!DBManager.loginUser(view.getNameField().getText(),view.getPasswordField().getText())){return;}

            /*MainScreenView msView = new MainScreenView();
            MainScreenPresenter msPresenter = new MainScreenPresenter(msView, model);
            view.getScene().setRoot(msView);
            try {
                msView.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
            } catch (MalformedURLException ex) {}
            msView.getScene().getWindow().sizeToScene();
            msView.getScene().getWindow().setX(uiSettings.getResX()/20);
            msView.getScene().getWindow().setY(uiSettings.getResY()/20);
            msView.getScene().getWindow().setHeight(9 * uiSettings.getResY()/10);
            msView.getScene().getWindow().setWidth(9 * uiSettings.getResX()/10);*/
            Router.getInstance().goTo(Screen.MAIN_MENU);

        });

        view.getRegisterButton().setOnAction(event -> {
            // Does not do anything if TextFields are empty
           if(!contentChecker()){return;}
           //Does not do anything if register fails
            if(!DBManager.registerUser(view.getNameField().getText(),view.getPasswordField().getText())){return;}
            //Loads Main Screen
            /*MainScreenView msView = new MainScreenView();
            MainScreenPresenter msPresenter = new MainScreenPresenter(msView, model);
            view.getScene().setRoot(msView);
            try {
                msView.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
            } catch (MalformedURLException ex) {}
            msView.getScene().getWindow().sizeToScene();
            msView.getScene().getWindow().setX(uiSettings.getResX()/20);
            msView.getScene().getWindow().setY(uiSettings.getResY()/20);
            msView.getScene().getWindow().setHeight(9 * uiSettings.getResY()/10);
            msView.getScene().getWindow().setWidth(9 * uiSettings.getResX()/10);*/
            Router.getInstance().goTo(Screen.MAIN_MENU);
        });
    }

    private Boolean contentChecker(){
        if (view.getNameField().getText().trim().isEmpty()||view.getPasswordField().getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Mandatory Field");
            alert.setContentText("Name and Password cant be empty");
            alert.showAndWait();
            return false;
        }
        else {return true;}
    }
}

