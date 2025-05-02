package be.kdg.integration2.mvpglobal.view.loginscreen;

import be.kdg.integration2.mvpglobal.model.BaseModel;
import be.kdg.integration2.mvpglobal.model.HumanPlayer;
import be.kdg.integration2.mvpglobal.model.MVPModel;
import be.kdg.integration2.mvpglobal.model.Screen;
import be.kdg.integration2.mvpglobal.utility.Router;
import be.kdg.integration2.mvpglobal.utility.dbconnection.DBManager;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class LoginScreenPresenter extends BasePresenter<LoginScreenView, BaseModel> {

    public LoginScreenPresenter(MVPModel model, LoginScreenView view) {
        super(view, model);

        if  (!DBManager.getInstance().isConnected()) {
            view.getPasswordField().setDisable(true);
            view.getPasswordLabel().setDisable(true);
            view.getRegisterButton().setDisable(true);
        }
    }

    /**
     * Calls login and register methods from DBManager when the user is trying to access the game
     */
    protected void addEventHandlers() {
        view.getLoginButton().setOnAction(event -> {
            if(!contentChecker()){return;}
            if (DBManager.getInstance().isConnected()) {
                if(!DBManager.getInstance().loginUser(view.getNameField().getText(),view.getPasswordField().getText())){return;}
}           else {
                new HumanPlayer(view.getNameField().getText());
            }
            Router.getInstance().goTo(Screen.MAIN_MENU);

        });

        view.getRegisterButton().setOnAction(event -> {
           if(!contentChecker()){return;}
            if(!DBManager.getInstance().registerUser(view.getNameField().getText(),view.getPasswordField().getText())){return;}
            Router.getInstance().goTo(Screen.MAIN_MENU);
        });
    }

    /**
     * Checks if Name and Password field are filled in, if not it will throw an error alert
     * @return
     */
    private Boolean contentChecker(){
        if (view.getNameField().getText().trim().isEmpty()||
                (DBManager.getInstance().isConnected()) && view.getPasswordField().getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Mandatory Field");
            alert.setContentText("Name and Password cant be empty");
            alert.showAndWait();
            return false;
        }
        else {return true;}
    }



    protected void updateView() {
        if (view.getScene() == null) {
            Platform.runLater(this::updateView);
            return;
        }

        Stage stage = (Stage) view.getScene().getWindow();
        stage.setResizable(true);
    }

}

