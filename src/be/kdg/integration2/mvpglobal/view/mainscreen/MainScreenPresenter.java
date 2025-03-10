package be.kdg.integration2.mvpglobal.view.mainscreen;

import be.kdg.integration2.mvpglobal.model.GameSession;
import be.kdg.integration2.mvpglobal.model.MVPModel;
import be.kdg.integration2.mvpglobal.view.UISettings;
import be.kdg.integration2.mvpglobal.view.aboutscreen.AboutScreenPresenter;
import be.kdg.integration2.mvpglobal.view.aboutscreen.AboutScreenView;
import be.kdg.integration2.mvpglobal.view.gamesetupscreen.GameSetupPresenter;
import be.kdg.integration2.mvpglobal.view.gamesetupscreen.GameSetupView;
import be.kdg.integration2.mvpglobal.view.infoscreen.InfoScreenPresenter;
import be.kdg.integration2.mvpglobal.view.infoscreen.InfoScreenView;
import be.kdg.integration2.mvpglobal.view.mainmenu.MainMenuPresenter;
import be.kdg.integration2.mvpglobal.view.mainmenu.MainMenuView;
import be.kdg.integration2.mvpglobal.view.settingsscreen.SettingsPresenter;
import be.kdg.integration2.mvpglobal.view.settingsscreen.SettingsView;
import be.kdg.integration2.mvpglobal.view.statscreen.StatPresenter;
import be.kdg.integration2.mvpglobal.view.statscreen.StatView;
import be.kdg.integration2.mvpglobal.view.statscreen.TablePresenter;
import be.kdg.integration2.mvpglobal.view.statscreen.TabletView;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Formatter;
import java.util.List;

public class MainScreenPresenter {

    private MVPModel model;
    private MainScreenView view;
    private UISettings uiSettings;

    public MainScreenPresenter(MVPModel model, MainScreenView view, UISettings uiSettings) {
        this.model = model;
        this.view = view;
        this.uiSettings = uiSettings;
        updateView();
        EventHandlers();
    }

    private void updateView() {
     }

    private void EventHandlers() {
        view.getTestButton().setOnAction (event -> {
            GameSession gameSession = new GameSession();
            gameSession.play();
        }); // just test code, needs another proper place in your code!!
        view.getSettingsItem().setOnAction(event -> {
                SettingsView settingsView = new SettingsView(uiSettings);
                SettingsPresenter presenter = new SettingsPresenter(this.model, settingsView, uiSettings);
                Stage settingsStage = new Stage();
                settingsStage.setTitle("Settings");
                settingsStage.initOwner(view.getScene().getWindow());
                settingsStage.initModality(Modality.APPLICATION_MODAL);
                Scene scene = new Scene(settingsView);
                settingsStage.setScene(scene);
                settingsStage.setTitle(uiSettings.getApplicationName() + " - Settings");
                settingsStage.setX(view.getScene().getWindow().getX() + uiSettings.getResX() / 10);
                settingsStage.setY(view.getScene().getWindow().getY() + uiSettings.getResY() / 10);
                if (Files.exists(uiSettings.getApplicationIconPath())) {
                    try {
                        settingsStage.getIcons().add(new Image(uiSettings.getApplicationIconPath().toUri().toURL().toString()));
                    } catch (MalformedURLException ex) {
                        // do nothing, if toURL-conversion fails, program can continue
                    }
                } else { // do nothing, if ApplicationIconImage is not available, program can continue
                }
                settingsView.getScene().getWindow().setHeight(7 * uiSettings.getResY() / 10);
                settingsView.getScene().getWindow().setWidth(7 * uiSettings.getResX() / 10);
                if (uiSettings.styleSheetAvailable()) {
                    settingsStage.getScene().getStylesheets().removeAll();
                    try {
                        settingsStage.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                    } catch (MalformedURLException ex) {
                        // do nothing, if toURL-conversion fails, program can continue
                    }
                }
                settingsStage.showAndWait();
                if (uiSettings.styleSheetAvailable()) {
                    view.getScene().getStylesheets().removeAll();
                    try {
                        view.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                    } catch (MalformedURLException ex) {
                        // do nothing, if toURL-conversion fails, program can continue
                    }
                }
        });
        view.getLoadItem().setOnAction(event -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Load Data File");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Textfiles", "*.txt"),
                        new FileChooser.ExtensionFilter("All Files", "*.*"));
                File selectedFile = fileChooser.showOpenDialog(view.getScene().getWindow());
                if ((selectedFile != null) ){ //^ (Files.isReadable(Paths.get(selectedFile.toURI())))) {
                    try {
                        List<String> input = Files.readAllLines(Paths.get(selectedFile.toURI()));
                        // Start implementation read data to be transfered to model
                        for (int i = 0; i < input.size(); i++) {
                            String inputline = input.get(i);
                            String[] elementen = inputline.split(" ");
                        }
                        // End implementation read data to be transfered to model
                    } catch (IOException e) {
                        //
                    }
                } else {
                    Alert errorWindow = new Alert(Alert.AlertType.ERROR);
                    errorWindow.setHeaderText("Problem with the selected input file:");
                    errorWindow.setContentText("File is not readable");
                    errorWindow.showAndWait();
                }
        });
        view.getSaveItem().setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Data File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Textfiles", "*.txt"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));
            File selectedFile = fileChooser.showSaveDialog(view.getScene().getWindow());
            if ((selectedFile != null) ^ (Files.isWritable(Paths.get(selectedFile.toURI())))) {
                try {
                    Files.deleteIfExists(Paths.get(selectedFile.toURI()));
                } catch (IOException e) {
                    //
                }
                try (Formatter output = new Formatter(selectedFile)) {
                    // Start implementation write data coming from the model
                    output.format("%s%n", "Here comes the data!");
                    output.format("%s%n", "First record");
                    output.format("%s%n", "...");
                    output.format("%s%n", "Last record");
                    // End implementation write data coming from the model
                } catch (IOException e) {
                    //
                }
            } else {
                Alert errorWindow = new Alert(Alert.AlertType.ERROR);
                errorWindow.setHeaderText("Problem with the selected output file:");
                errorWindow.setContentText("File is not writable");
                errorWindow.showAndWait();
            }

                });
        view.getExitItem().setOnAction(event -> handleCloseEvent(event));

        view.getAboutItem().setOnAction(event -> {
                AboutScreenView aboutScreenView = new AboutScreenView(uiSettings);
                AboutScreenPresenter aboutScreenPresenter = new AboutScreenPresenter(model, aboutScreenView, uiSettings);
                Stage aboutScreenStage = new Stage();
                aboutScreenStage.initOwner(view.getScene().getWindow());
                aboutScreenStage.initModality(Modality.APPLICATION_MODAL);
                Scene scene = new Scene(aboutScreenView);
                aboutScreenStage.setScene(scene);
                aboutScreenStage.setTitle(uiSettings.getApplicationName() + " - About");
                aboutScreenStage.setX(view.getScene().getWindow().getX() + uiSettings.getResX() / 10);
                aboutScreenStage.setY(view.getScene().getWindow().getY() + uiSettings.getResY() / 10);
                if (Files.exists(uiSettings.getApplicationIconPath())) {
                    try {
                        aboutScreenStage.getIcons().add(new Image(uiSettings.getApplicationIconPath().toUri().toURL().toString()));
                    } catch (MalformedURLException ex) {
                        // do nothing, if toURL-conversion fails, program can continue
                    }
                } else { // do nothing, if ApplicationIconImage is not available, program can continue
                }
                aboutScreenView.getScene().getWindow().setHeight(uiSettings.getResY() / 2);
                aboutScreenView.getScene().getWindow().setWidth(uiSettings.getResX() / 2);
                if (uiSettings.styleSheetAvailable()) {
                    aboutScreenView.getScene().getStylesheets().removeAll();
                    try {
                        aboutScreenView.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                    } catch (MalformedURLException ex) {
                        // do nothing, if toURL-conversion fails, program can continue
                    }
                }
                aboutScreenStage.showAndWait();
        });
        view.getInfoItem().setOnAction(event -> {
                InfoScreenView infoScreenView = new InfoScreenView(uiSettings);
                InfoScreenPresenter infoScreenPresenter = new InfoScreenPresenter(model, infoScreenView, uiSettings);
                Stage infoScreenStage = new Stage();
                infoScreenStage.initOwner(view.getScene().getWindow());
                infoScreenStage.initModality(Modality.APPLICATION_MODAL);
                Scene scene = new Scene(infoScreenView);
                infoScreenStage.setScene(scene);
                infoScreenStage.setTitle(uiSettings.getApplicationName()+ " - Info");
                infoScreenStage.setX(view.getScene().getWindow().getX() + uiSettings.getResX() / 10);
                infoScreenStage.setY(view.getScene().getWindow().getY() + uiSettings.getResY() / 10);
                if (Files.exists(uiSettings.getApplicationIconPath())) {
                    try {
                        infoScreenStage.getIcons().add(new Image(uiSettings.getApplicationIconPath().toUri().toURL().toString()));
                    }
                    catch (MalformedURLException ex) {
                        // do nothing, if toURL-conversion fails, program can continue
                    }
                } else { // do nothing, if ApplicationIconImage is not available, program can continue
                }
                infoScreenView.getScene().getWindow().setHeight(uiSettings.getResY()/2);
                infoScreenView.getScene().getWindow().setWidth(uiSettings.getResX()/2);
                if (uiSettings.styleSheetAvailable()){
                    infoScreenView.getScene().getStylesheets().removeAll();
                    try {
                        infoScreenView.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                    }
                    catch (MalformedURLException ex) {
                        // do nothing, if toURL-conversion fails, program can continue
                    }
                }
                infoScreenStage.showAndWait();
        });


        view.getStatisticsItem().setOnAction(event -> {
            StatView statView = new StatView(uiSettings);
            try {
                StatPresenter statPresenter = new StatPresenter(model , statView , uiSettings);
                statView.getserieC(statPresenter.stat1());
                statView.draw(statPresenter.stat1());

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Stage statStage = new Stage();
            statStage.setTitle("Setatistics");
            statStage.initOwner(view.getScene().getWindow());
            statStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene1 = new Scene(statView);
            statStage.setScene(scene1);
            statStage.setTitle(uiSettings.getApplicationName() + " - Statistics");
            statStage.setX(view.getScene().getWindow().getX() + uiSettings.getResX() / 10);
            statStage.setY(view.getScene().getWindow().getY() + uiSettings.getResY() / 10);
         //   statStage.setHeight(uiSettings.getResY()/2);
        //    statStage.setWidth(uiSettings.getResX()/2);
            if (Files.exists(uiSettings.getApplicationIconPath())) {
                try {
                    statStage.getIcons().add(new Image(uiSettings.getApplicationIconPath().toUri().toURL().toString()));
                } catch (MalformedURLException ex) {
                    // do nothing, if toURL-conversion fails, program can continue
                }
            } else { // do nothing, if ApplicationIconImage is not available, program can continue
            }
            statStage.getScene().getWindow().setHeight(7 * uiSettings.getResY() / 10);
            statStage.getScene().getWindow().setWidth(7 * uiSettings.getResX() / 10);
            if (uiSettings.styleSheetAvailable()) {
                statStage.getScene().getStylesheets().removeAll();
                try {
                    statStage.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                } catch (MalformedURLException ex) {
                    // do nothing, if toURL-conversion fails, program can continue
                }
            }
            statStage.showAndWait();
            if (uiSettings.styleSheetAvailable()) {
                view.getScene().getStylesheets().removeAll();
                try {
                    view.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                } catch (MalformedURLException ex) {
                    // do nothing, if toURL-conversion fails, program can continue
                }
            }









        });



        view.getTableItem().setOnAction(event -> {
            TabletView tableView = null;
            try {
                tableView = new TabletView(uiSettings);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            TablePresenter statPresenter = new TablePresenter(model , uiSettings , tableView);


            Stage tableStage = new Stage();
            tableStage.setTitle("table");
            tableStage.initOwner(view.getScene().getWindow());
            tableStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene2 = new Scene(tableView);
            tableStage.setScene(scene2);
            tableStage.setTitle(uiSettings.getApplicationName() + " - table");
            tableStage.setX(view.getScene().getWindow().getX() + uiSettings.getResX() / 10);
            tableStage.setY(view.getScene().getWindow().getY() + uiSettings.getResY() / 10);
            //   statStage.setHeight(uiSettings.getResY()/2);
            //    statStage.setWidth(uiSettings.getResX()/2);
            if (Files.exists(uiSettings.getApplicationIconPath())) {
                try {
                    tableStage.getIcons().add(new Image(uiSettings.getApplicationIconPath().toUri().toURL().toString()));
                } catch (MalformedURLException ex) {
                    // do nothing, if toURL-conversion fails, program can continue
                }
            } else { // do nothing, if ApplicationIconImage is not available, program can continue
            }
            tableStage.getScene().getWindow().setHeight(7 * uiSettings.getResY() / 10);
            tableStage.getScene().getWindow().setWidth(7 * uiSettings.getResX() / 10);
            if (uiSettings.styleSheetAvailable()) {
                tableStage.getScene().getStylesheets().removeAll();
                try {
                    tableStage.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                } catch (MalformedURLException ex) {
                    // do nothing, if toURL-conversion fails, program can continue
                }
            }
            tableStage.showAndWait();
            if (uiSettings.styleSheetAvailable()) {
                view.getScene().getStylesheets().removeAll();
                try {
                    view.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                } catch (MalformedURLException ex) {
                    // do nothing, if toURL-conversion fails, program can continue
                }
            }
        });

        view.getGameSetupMI().setOnAction(event -> {
            GameSetupView gameSetupView = new GameSetupView(uiSettings);
            view.getStylesheets().add("be/kdg/integration2/mvpglobal/view/base/style.css");
            GameSetupPresenter gameSetupPresenter = new GameSetupPresenter(gameSetupView, model, uiSettings);
            Stage gameSetupStage = new Stage();
            gameSetupStage.initOwner(view.getScene().getWindow());
            gameSetupStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(gameSetupView);
            gameSetupStage.setScene(scene);
            gameSetupStage.setTitle(uiSettings.getApplicationName()+ " - Info");
            gameSetupStage.setX(view.getScene().getWindow().getX() + uiSettings.getResX() / 10);
            gameSetupStage.setY(view.getScene().getWindow().getY() + uiSettings.getResY() / 10);
            if (Files.exists(uiSettings.getApplicationIconPath())) {
                try {
                    gameSetupStage.getIcons().add(new Image(uiSettings.getApplicationIconPath().toUri().toURL().toString()));
                }
                catch (MalformedURLException ex) {
                    // do nothing, if toURL-conversion fails, program can continue
                }
            } else { // do nothing, if ApplicationIconImage is not available, program can continue
            }
            gameSetupView.getScene().getWindow().setHeight(uiSettings.getResY()/2);
            gameSetupView.getScene().getWindow().setWidth(uiSettings.getResX()/2);
            if (uiSettings.styleSheetAvailable()){
                gameSetupView.getScene().getStylesheets().removeAll();
                try {
                    gameSetupView.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                }
                catch (MalformedURLException ex) {
                    // do nothing, if toURL-conversion fails, program can continue
                }
            }
            gameSetupStage.showAndWait();
        });

        view.getMenuMI().setOnAction(event -> {
            MainMenuView menuView = new MainMenuView();
            view.getStylesheets().add("be/kdg/integration2/mvpglobal/view/base/style.css");
            MainMenuPresenter menuPresenter = new MainMenuPresenter(menuView, model, uiSettings);
            Stage menuStage = new Stage();
            menuStage.initOwner(view.getScene().getWindow());
            menuStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(menuView);
            menuStage.setScene(scene);
            menuStage.setTitle(uiSettings.getApplicationName()+ " - Info");
            menuStage.setX(view.getScene().getWindow().getX() + uiSettings.getResX() / 10);
            menuStage.setY(view.getScene().getWindow().getY() + uiSettings.getResY() / 10);
            if (Files.exists(uiSettings.getApplicationIconPath())) {
                try {
                    menuStage.getIcons().add(new Image(uiSettings.getApplicationIconPath().toUri().toURL().toString()));
                }
                catch (MalformedURLException ex) {
                    // do nothing, if toURL-conversion fails, program can continue
                }
            } else { // do nothing, if ApplicationIconImage is not available, program can continue
            }
            menuView.getScene().getWindow().setHeight(uiSettings.getResY()/2);
            menuView.getScene().getWindow().setWidth(uiSettings.getResX()/2);
            if (uiSettings.styleSheetAvailable()){
                menuView.getScene().getStylesheets().removeAll();
                try {
                    menuView.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                }
                catch (MalformedURLException ex) {
                    // do nothing, if toURL-conversion fails, program can continue
                }
            }
            menuStage.showAndWait();
        });
    }

    public void windowsHandler() {
        view.getScene().getWindow().setOnCloseRequest(event -> handleCloseEvent(event));
    }

    private void handleCloseEvent(Event event){
        final Alert stopWindow = new Alert(Alert.AlertType.CONFIRMATION);
        stopWindow.setHeaderText("You're closing the application.");
        stopWindow.setContentText("Are you sure? Unsaved data may be lost.");
        stopWindow.setTitle("WARNING!");
        stopWindow.getButtonTypes().clear();
        ButtonType noButton = new ButtonType("No");
        ButtonType yesButton = new ButtonType("Yes");
        stopWindow.getButtonTypes().addAll(yesButton, noButton);
        stopWindow.showAndWait();
        if (stopWindow.getResult() == null || stopWindow.getResult().equals(noButton)) {
            event.consume();
        } else {
            view.getScene().getWindow().hide();
        }
    }
}
