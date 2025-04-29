package be.kdg.integration2.mvpglobal.view.settingsscreen;

import be.kdg.integration2.mvpglobal.model.*;
import be.kdg.integration2.mvpglobal.view.UISettings;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;
import be.kdg.integration2.mvpglobal.view.rules.RulesView;
import javafx.event.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Paths;

public class SettingsPresenter extends BasePresenter<SettingsView, BaseModel> {



    public SettingsPresenter(MVPModel model, SettingsView view) {
        super(view, model);
        updateView();
        EventHandlers();
    }

    protected void updateView() {
    }

    protected void EventHandlers() {
        view.getExitItem().setOnAction(event -> handleCloseEvent(event));
        view.getMenuButton().setOnAction(e -> goToMenu());

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

    private void goToMenu() {
        Router.getInstance().goTo(Screen.MAIN_MENU);
    }

    private void handleCloseEvent(Event event){
        view.getScene().getWindow().hide();
    }
}
