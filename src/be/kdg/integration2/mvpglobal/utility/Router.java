package be.kdg.integration2.mvpglobal.utility;

import be.kdg.integration2.mvpglobal.model.MVPModel;
import be.kdg.integration2.mvpglobal.model.Screen;
import be.kdg.integration2.mvpglobal.view.UISettings;
import be.kdg.integration2.mvpglobal.view.mainscreen.MainScreenPresenter;
import be.kdg.integration2.mvpglobal.view.mainscreen.MainScreenView;
import javafx.stage.Stage;

public class Router {
    public static Router Instance = null;
    private final Stage primaryStage;

    public Router(Stage primaryStage) {
        Instance = this;
        this.primaryStage = primaryStage;
    }

    public static Router getInstance() {
        if (Instance == null) {
            // throw an exception or smt
        }
        return Instance;
    }

    public void goTo(Screen screen) {
        goTo(screen, null);
    }

    public void goTo(Screen screenType, Object data){
        var presenter = screenType.createPresenter();
        UISettings uiSettings = UISettings.getInstance();

        presenter.init(data);
        if(screenType == Screen.GAME) {
            primaryStage.setMinHeight(560);
            primaryStage.setMinWidth(1030);
            primaryStage.setWidth(1070);
            primaryStage.setHeight(700);
        } else {
            primaryStage.setMinHeight(500);
            primaryStage.setMinWidth(500);
            primaryStage.setHeight(uiSettings.getLowestRes() /2);
            primaryStage.setWidth(uiSettings.getLowestRes()/2);
            primaryStage.setResizable(true);
        }

        primaryStage.getScene().setRoot(presenter.getView());
    }

    public void mainScreen() {
        MainScreenPresenter presenter = new MainScreenPresenter(new MainScreenView(), new MVPModel());
        primaryStage.getScene().setRoot(presenter.getView());
    }
}
