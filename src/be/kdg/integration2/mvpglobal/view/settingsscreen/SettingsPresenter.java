package be.kdg.integration2.mvpglobal.view.settingsscreen;

import be.kdg.integration2.mvpglobal.model.*;
import be.kdg.integration2.mvpglobal.view.UISettings;
import javafx.event.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Paths;

public class SettingsPresenter {

    private MVPModel model;
    private SettingsView view;
    private UISettings uiSettings;

    public SettingsPresenter(MVPModel model, SettingsView view, UISettings uiSettings) {
        this.model = model;
        this.view = view;
        this.uiSettings = uiSettings;
        updateView();
        EventHandlers();
    }

    private void updateView() {
    }

    private void EventHandlers() {
        view.getExitItem().setOnAction(event -> handleCloseEvent(event));

        view.getCssButton().setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Style Sheet Files", "*.css"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));
            fileChooser.setInitialDirectory(new File("resources" + UISettings.FILE_SEPARATOR + "stylesheets"));
            File selectedFile = fileChooser.showOpenDialog(view.getScene().getWindow());
            if (selectedFile != null) {
                URI uri = selectedFile.toURI();
                uiSettings.setStyleSheetPath(Paths.get(uri));
            }
            if (uiSettings.styleSheetAvailable()){
                view.getScene().getStylesheets().removeAll();
                try{
                    view.getScene().getStylesheets().add(uiSettings.getStyleSheetPath().toUri().toURL().toString());
                } catch (MalformedURLException ex) {
                    // do nothing, if toURL-conversion fails, program can continue
                }
            }
        });

        view.getOkButton().setOnMouseClicked(event -> handleCloseEvent(event));
    }

    private void handleCloseEvent(Event event){
        view.getScene().getWindow().hide();
    }
}
