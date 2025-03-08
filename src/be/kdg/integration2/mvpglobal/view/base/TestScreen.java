package be.kdg.integration2.mvpglobal.view.base;

import be.kdg.integration2.mvpglobal.view.UISettings;
import be.kdg.integration2.mvpglobal.view.gamescreen.GameScreenPresenter;
import be.kdg.integration2.mvpglobal.view.gamescreen.GameScreenView;
import be.kdg.integration2.mvpglobal.view.gamescreen.TestModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestScreen extends Application {

    @Override
    public void start(Stage primaryStage) {
        UISettings uiSettings = new UISettings();
        TestModel model = new TestModel();
        GameScreenView view = new GameScreenView(uiSettings);
        view.getStylesheets().add("be/kdg/integration2/mvpglobal/view/base/style.css");

        Scene scene = new Scene(view);

        primaryStage.setScene(scene);
        primaryStage.setHeight(720);
        primaryStage.setWidth(1080);
        primaryStage.setTitle(uiSettings.getApplicationName());

        GameScreenPresenter presenter = new GameScreenPresenter(view, model, uiSettings);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
