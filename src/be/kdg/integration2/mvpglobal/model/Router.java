package be.kdg.integration2.mvpglobal.model;

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

        presenter.init(data);

        primaryStage.getScene().setRoot(presenter.getView());
    }

    public void mainScreen() {
        MainScreenPresenter presenter = new MainScreenPresenter(new MainScreenView(), new MVPModel());
        primaryStage.getScene().setRoot(presenter.getView());
    }
}
