package be.kdg.integration2.mvpglobal.utility;

import be.kdg.integration2.mvpglobal.model.Screen;
import be.kdg.integration2.mvpglobal.view.UISettings;
import javafx.stage.Stage;

/**
 * Router class for managing screen transitions in the application.
 */
public class Router {
    public static Router Instance = null;
    private final Stage primaryStage;

    /**
     * Initializes the Router class.
     *
     * @param primaryStage The primary stage of the application.
     */
    public Router(Stage primaryStage) {
        Instance = this;
        this.primaryStage = primaryStage;
    }

    /**
     * Returns the singleton instance of the Router class.
     *
     * @return The singleton instance of the Router class.
     */
    public static Router getInstance() {
        if (Instance == null) {
            // throw an exception or smt
        }
        return Instance;
    }

    /**
     * Navigates to the specified screen.
     *
     * @param screen The screen to navigate to.
     */
    public void goTo(Screen screen) {
        goTo(screen, null);
    }

    /**
     * Navigates to the specified screen with additional data.
     *
     * @param screenType The screen to navigate to.
     * @param data       Additional data to pass to the screen.
     */
    public void goTo(Screen screenType, Object data){
        var presenter = screenType.createPresenter();
        UISettings.getInstance();
        primaryStage.setOnCloseRequest(null);

        presenter.init(data);

        primaryStage.getScene().setRoot(presenter.getView());
    }

    /**
     * Returns the primary stage of the application.
     *
     * @return The primary stage of the application.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
