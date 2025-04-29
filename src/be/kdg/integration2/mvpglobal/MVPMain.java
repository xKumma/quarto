package be.kdg.integration2.mvpglobal;

import be.kdg.integration2.mvpglobal.utility.Router;
import be.kdg.integration2.mvpglobal.utility.dbconnection.DBManager;
//import be.kdg.integration2.mvpglobal.model.Router;
import be.kdg.integration2.mvpglobal.view.UISettings;
import be.kdg.integration2.mvpglobal.view.startscreen.StartScreenPresenter;
import be.kdg.integration2.mvpglobal.view.startscreen.StartScreenView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.sql.SQLException;

public class MVPMain extends Application {
    @Override
    public void start(Stage primaryStage) throws SQLException {
        UISettings uiSettings = UISettings.getInstance();
        DBManager.setupDatabase();

        StartScreenView view = new StartScreenView();

        Scene scene = new Scene(view);
        if (uiSettings.styleSheetAvailable()){
            try {
                scene.getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());

            } catch (MalformedURLException ex) {
                // do nothing, if toURL-conversion fails, program can continue
            }
        }

        primaryStage.setScene(scene);
        primaryStage.setHeight(uiSettings.getLowestRes() /2);
        primaryStage.setWidth(uiSettings.getLowestRes()/2);
        primaryStage.setTitle(uiSettings.getApplicationName());
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(500);
        primaryStage.setMaxHeight(1000);
        primaryStage.setMaxWidth(1900);
        primaryStage.setResizable(false);
        if (Files.exists(uiSettings.getApplicationIconPath())) {
             try {
                 primaryStage.getIcons().add(new Image(uiSettings.getApplicationIconPath().toUri().toURL().toString()));
             }
             catch (MalformedURLException ex) {
                 // do nothing, if toURL-conversion fails, program can continue
             }
        } else { // do nothing, if ApplicationIcon is not available, program can continue
        }
        StartScreenPresenter presenter = new StartScreenPresenter(view, null);
        presenter.init();
        //presenter.windowsHandler();
        new Router(primaryStage);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
