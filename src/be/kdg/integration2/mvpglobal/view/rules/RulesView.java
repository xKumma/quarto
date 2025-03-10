package be.kdg.integration2.mvpglobal.view.rules;

import be.kdg.integration2.mvpglobal.view.components.Header;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class RulesView extends BorderPane {

    Button menuButton;
    TextArea rulesTextArea;

    public RulesView() {
        initialiseNodes();
        layoutNodes();
    }

    protected void initialiseNodes() {
        Button menuButton = new Button("Menu");
        String rulesText = "In Quarto, you play against a computer on a 4x4 grid with 16 unique pieces, each varying by color, height, shape, and top style. The goal is to create a row, column, or diagonal of four pieces sharing a common attribute. Depending on who starts the game, either you or the computer selects a piece for the other to place on the board. Turns alternate as each player places the given piece and then chooses a piece for their opponent. If all pieces are placed without a win, the game is a draw.";
        rulesTextArea = new TextArea(rulesText);

        rulesTextArea.setWrapText(true); // Enables line wrapping
        rulesTextArea.setEditable(false); // Makes the text read-only
    }

    protected void layoutNodes() {
        setTop(new Header());
        VBox rulesVBox = new VBox(new Label("Menu"), rulesTextArea);
        setCenter(rulesVBox);
        BorderPane.setAlignment(rulesVBox, Pos.CENTER);
        setBottom(menuButton);

    }

    public TextArea getRulesTextArea() {
        return rulesTextArea;
    }

    public Button getMenuButton() {
        return menuButton;
    }
}
