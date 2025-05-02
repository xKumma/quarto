package be.kdg.integration2.mvpglobal.view.mainmenu;

import be.kdg.integration2.mvpglobal.view.base.BaseView;
import be.kdg.integration2.mvpglobal.view.components.Header;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MainMenuView extends BaseView {

    private Header header;
    private Button startGameButton;
    private Button rulesButton;
    private Button leaderboardButton;
    private Button quitButton;
    /* --- buttons for testing ---
    private Button statisticsButton;
    private Button tableButton;
    private Button aboutButton;
    private Button settingsButton;
    */

    //add aboutscreen and settings screen

    public MainMenuView() {
        super();
    }

    protected void initialiseNodes() {
        header = new Header();
        startGameButton = new Button("Start Game");
        rulesButton = new Button("Rules");
        leaderboardButton = new Button("Leaderboard");
        quitButton = new Button("Quit");
        /* --- buttons for testing ---
        statisticsButton = new Button("Statistics (temp)");
        tableButton = new Button("Table (temp)");
        aboutButton = new Button("About (temp)");
        settingsButton = new Button("Settings (temp)");
        */
    }

    protected void layoutNodes() {
        //resize buttons
        startGameButton.setPrefSize(110, 20); // Width, Height
        rulesButton.setPrefSize(110, 20);
        leaderboardButton.setPrefSize(110, 20);
        quitButton.setPrefSize(110, 20);
        /* --- buttons for testing ---
        statisticsButton.setPrefSize(110, 20);
        tableButton.setPrefSize(110, 20);
        aboutButton.setPrefSize(110, 20);
        settingsButton.setPrefSize(110, 20);
        */


        //rectangle
        Rectangle rectangle = new Rectangle();
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(3);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setRotate(45);

        //buttons vbox
        VBox buttons = new VBox(header,new Label("Menu"),startGameButton, rulesButton, leaderboardButton, quitButton);
        buttons.setSpacing(4);
        buttons.setAlignment(Pos.CENTER);
        buttons.setMaxWidth(400);
        buttons.setMaxHeight(400);

        rectangle.widthProperty().bind(buttons.widthProperty().add(20));
        rectangle.heightProperty().bind(buttons.heightProperty().add(20));

        //setTop(header);
        StackPane root = new StackPane(rectangle, buttons);
        StackPane.setAlignment(buttons, Pos.CENTER);
        StackPane.setAlignment(rectangle, Pos.CENTER);
        setCenter(root);


    }

    Header getHeader() {
        return header;
    }
    /* --- buttons for testing ---
    public Button getSettingsButton() {
        return settingsButton;
    }

    public Button getAboutButton() {
        return aboutButton;
    }

    Button getTableButton() {
        return tableButton;
    }

    Button getStatisticsButton() {
        return statisticsButton;
    }
    */
    Button getQuitButton() {
        return quitButton;
    }

    Button getLeaderboardButton() {
        return leaderboardButton;
    }

    Button getRulesButton() {
        return rulesButton;
    }

    Button getStartGameButton() {
        return startGameButton;
    }
}
