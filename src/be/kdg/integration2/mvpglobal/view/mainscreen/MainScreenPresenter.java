package be.kdg.integration2.mvpglobal.view.mainscreen;

import be.kdg.integration2.mvpglobal.model.MVPModel;
import be.kdg.integration2.mvpglobal.model.Screen;
import be.kdg.integration2.mvpglobal.utility.Router;
import be.kdg.integration2.mvpglobal.view.aboutscreen.AboutScreenPresenter;
import be.kdg.integration2.mvpglobal.view.aboutscreen.AboutScreenView;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;
import be.kdg.integration2.mvpglobal.view.infoscreen.InfoScreenPresenter;
import be.kdg.integration2.mvpglobal.view.infoscreen.InfoScreenView;
import be.kdg.integration2.mvpglobal.view.settingsscreen.SettingsPresenter;
import be.kdg.integration2.mvpglobal.view.settingsscreen.SettingsView;
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
import java.util.Formatter;
import java.util.List;

public class MainScreenPresenter extends BasePresenter<MainScreenView, MVPModel> {
    public MainScreenPresenter(MainScreenView view, MVPModel model) {
        super(view, model);
    }


    protected void addEventHandlers() {

        view.getSettingsItem().setOnAction(event -> {
                SettingsView settingsView = new SettingsView();
                SettingsPresenter presenter = new SettingsPresenter(this.model, settingsView);
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
                AboutScreenView aboutScreenView = new AboutScreenView();
                AboutScreenPresenter aboutScreenPresenter = new AboutScreenPresenter(model, aboutScreenView);
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
                InfoScreenView infoScreenView = new InfoScreenView();
                InfoScreenPresenter infoScreenPresenter = new InfoScreenPresenter(infoScreenView, null);
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
            Router.getInstance().goTo(Screen.END_SCREEN);
        });


        view.getTableItem().setOnAction(event -> {
            TabletView tableView = null;

            tableView = new TabletView();


            TablePresenter statPresenter = new TablePresenter(model, tableView);


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
            Router.getInstance().goTo(Screen.GAME_SETUP);
        });

        view.getMenuMI().setOnAction(event -> {
            Router.getInstance().goTo(Screen.MAIN_MENU);
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
