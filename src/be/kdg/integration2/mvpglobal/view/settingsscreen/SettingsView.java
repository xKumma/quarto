package be.kdg.integration2.mvpglobal.view.settingsscreen;

import be.kdg.integration2.mvpglobal.view.UISettings;
import be.kdg.integration2.mvpglobal.view.base.BaseView;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Insets;

public class SettingsView extends BaseView {

    private MenuItem exitMI;
    private TextField cssName;
    private Button cssButton;
    private Button okButton;
    private Button menuButton;

    public SettingsView() {
        super();
        initialiseNodes();
        layoutNodes();
    }

    protected void initialiseNodes() {
        this.exitMI = new MenuItem("Exit");
        this.cssButton = new Button("Select File");
        this.cssName = new TextField();
        this.cssName.setPrefWidth(uiSettings.getLowestRes() / 3);
        this.cssName.setText(uiSettings.getStyleSheetPath().toString());
        this.okButton = new Button("OK (quit)");
        menuButton = new Button("Menu");
    }

    protected void layoutNodes() {
        Menu menuFile = new Menu("File");
        menuFile.getItems().addAll(exitMI);
        MenuBar menuBar = new MenuBar(menuFile);
        setTop(menuBar);
        HBox cssSettings = new HBox();
        cssSettings.setSpacing(uiSettings.getLowestRes() / 100);
        cssSettings.setPadding(new Insets(20));
        Label cssLabel = new Label("Style Sheet File Name:");
        cssSettings.getChildren().addAll(cssLabel, cssName, cssButton);
        this.setCenter(cssSettings);
        HBox buttons = new HBox(okButton, menuButton);
        buttons.setSpacing(4);
        buttons.setAlignment(Pos.BOTTOM_RIGHT);
        this.setBottom(buttons);
    }

    Button getMenuButton() {
        return menuButton;
    }
    MenuItem getExitItem() {return exitMI;}
    Button getCssButton () {return cssButton;}
    TextField getCssName () {return cssName;}
    Button getOkButton () {return okButton;}
}
