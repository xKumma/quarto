package be.kdg.integration2.mvpglobal.view.rules;

import be.kdg.integration2.mvpglobal.view.base.BaseView;
import be.kdg.integration2.mvpglobal.view.components.Header;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class RulesView extends BaseView {

    private Button menuButton;
    private Label rulesTextLabel;
    private Label rulesTitleLabel;
    private Header header;

    public RulesView() {
        super();
    }

    protected void initialiseNodes() {
        Font font = new Font(16);
        menuButton = new Button("Menu");
        String rulesText = "In Quarto, you play against a computer on a 4x4 grid with 16 unique pieces, each varying by color, height, shape, and top style. The goal is to create a row, column, or diagonal of four pieces sharing a common attribute. Depending on who starts the game, either you or the computer selects a piece for the other to place on the board. Turns alternate as each player places the given piece and then chooses a piece for their opponent. If all pieces are placed without a win, the game is a draw.";
        rulesTextLabel = new Label(rulesText);
        rulesTextLabel.setWrapText(true);  // Enable text wrapping
        rulesTextLabel.setFont(font); // Adjust the font size as needed

        rulesTitleLabel = new Label("Rules");
        rulesTitleLabel.setFont(font);
        header = new Header();

    }

    protected void layoutNodes() {
        setTop(header);

        VBox rulesVBox = new VBox(rulesTitleLabel, rulesTextLabel);
        rulesVBox.setSpacing(15);
        rulesVBox.setAlignment(Pos.CENTER);
        menuButton.setAlignment(Pos.BOTTOM_CENTER);


        setCenter(rulesVBox);
        setBottom(menuButton);


        setPadding(new Insets(50));

    }

    Header getHeader() {
        return header;
    }

    Label getRulesTitleLabel() {
        return rulesTitleLabel;
    }

    Label getRulesTextLabel() {
        return rulesTextLabel;
    }

    Button getMenuButton() {
        return menuButton;
    }
}
