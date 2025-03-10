package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.view.UISettings;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;
import be.kdg.integration2.mvpglobal.view.gamescreen.GameScreenPresenter;
import be.kdg.integration2.mvpglobal.view.gamescreen.GameScreenView;
import be.kdg.integration2.mvpglobal.view.gamesetupscreen.GameSetupPresenter;
import be.kdg.integration2.mvpglobal.view.gamesetupscreen.GameSetupView;
import javafx.stage.Stage;

public class Router {
    public static Router Instance;
    private final Stage primaryStage;

    public Router(Stage primaryStage) {
        Instance = this;
        this.primaryStage = primaryStage;
    }

    public void goTo(Screen screenType, Object data){
        var presenter = new BasePresenter();

        switch(screenType){
            case GAME -> {
                presenter = new GameScreenPresenter(new GameScreenView(new UISettings()), new GameSession(), new UISettings());
            }
            case GAME_SETUP -> {
                presenter = new GameSetupPresenter(new GameSetupView(new UISettings()), new GameSetup(), new UISettings());
            }
            case MAIN_MENU -> {
            }
            case LEADERBOARD -> {
            }
            case END_SCREEN -> {
            }
            case RULES -> {
            }
            case LOGIN -> {
            }
        }


        primaryStage.getScene().setRoot(presenter.getView());
    }
}
