package be.kdg.integration2.mvpglobal.view.aboutscreen;

import java.net.MalformedURLException;
import java.nio.file.Files;
import be.kdg.integration2.mvpglobal.view.UISettings;
import be.kdg.integration2.mvpglobal.view.base.BaseView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AboutScreenView extends BaseView {

    private Button okButton;
    private Button menuButton;

    public AboutScreenView() {
        super();
        initialiseNodes();
        layoutNodes();
    }

    protected void initialiseNodes() {
        okButton = new Button("OK (quit)");
        okButton.setPrefWidth(100);
        menuButton = new Button("Menu");
        menuButton.setPrefWidth(60);
    }

    protected void layoutNodes() {
        BorderPane centralPane = new BorderPane();
         if (Files.exists(uiSettings.getAboutImagePath())) {
            try {
                centralPane.setCenter(new ImageView(new Image(uiSettings.getAboutImagePath().toUri().toURL().toString())));
            }
            catch (MalformedURLException ex) {
                // do nothing, if toURL-conversion fails, program can continue
            }
        } else { // do nothing, if AboutImage is not available, program can continue
        }
        VBox labelsPane = new VBox();
        labelsPane.getChildren().addAll(new Label("This application was developed by WDK"),
                                        new Label(String.format("Free of %c", 169)),
                                        new Label("Enjoy!"));
        centralPane.setBottom(labelsPane);
        setCenter(centralPane);
        setPadding(new Insets(uiSettings.getInsetsMargin()));
        HBox buttons = new HBox(okButton, menuButton);
        buttons.setSpacing(4);
        BorderPane.setAlignment(buttons, Pos.CENTER_RIGHT);
        BorderPane.setMargin(buttons, new Insets(uiSettings.getInsetsMargin(), 0, 0, 0));
        setBottom(buttons);
        setPrefWidth(uiSettings.getLowestRes() / 4);
        setPrefHeight(uiSettings.getLowestRes() / 4);
    }

    Button getMenuButton() {
        return menuButton;
    }

    Button getBtnOk() {
        return okButton;
    }
}
