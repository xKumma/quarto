package be.kdg.integration2.mvpglobal.view.infoscreen;

import be.kdg.integration2.mvpglobal.view.base.BaseView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

public class InfoScreenView extends BaseView {

    private TextArea InfoText;
    private Button okButton;

    public InfoScreenView() {
        super();
    }

    protected void initialiseNodes() {
        InfoText = new TextArea("poc");
        okButton = new Button("OK");
        okButton.setPrefWidth(60);
    }

    protected void layoutNodes() {
        setCenter(InfoText);
        InfoText.setPrefWidth(Double.MAX_VALUE);
        InfoText.setPrefHeight(Double.MAX_VALUE);
        InfoText.setWrapText(true);
        InfoText.setFont(Font.font("Arial", 12));
        InfoText.setEditable(false);
        setPadding(new Insets(uiSettings.getInsetsMargin()));
        BorderPane.setAlignment(okButton, Pos.CENTER_RIGHT);
        BorderPane.setMargin(okButton, new Insets(uiSettings.getInsetsMargin(), 0, 0, 0));
        setBottom(okButton);
        setPrefWidth(uiSettings.getLowestRes() / 4);
        setPrefHeight(uiSettings.getLowestRes() / 4);
    }

    TextArea getInfoText () {return InfoText;}

    Button getBtnOk() {
        return okButton;
    }
}
