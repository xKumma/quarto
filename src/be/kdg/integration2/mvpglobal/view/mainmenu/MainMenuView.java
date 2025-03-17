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

    private Button startGameButton;
    private Button rulesButton;
    private Button leaderboardButton;
    private Button quitButton;
    private Button statisticsButton;
    private Button tableButton;
    private Header header;

    public MainMenuView() {
        super();
    }

    protected void initialiseNodes() {
        startGameButton = new Button("Start Game");
        rulesButton = new Button("Rules");
        leaderboardButton = new Button("Leaderboard");
        quitButton = new Button("Quit");
        statisticsButton = new Button("Statistics");
        tableButton = new Button("Table");
        header = new Header();

    }

    protected void layoutNodes() {
        //resize buttons
        startGameButton.setPrefSize(110, 20); // Width, Height
        rulesButton.setPrefSize(110, 20);
        leaderboardButton.setPrefSize(110, 20);
        quitButton.setPrefSize(110, 20);
        statisticsButton.setPrefSize(110, 20);
        tableButton.setPrefSize(110, 20);



        //rectangle
        Rectangle rectangle = new Rectangle();
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(3);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setRotate(45);

        //buttons vbox
        VBox buttons = new VBox(header,new Label("Menu"),startGameButton, rulesButton, leaderboardButton, quitButton, statisticsButton, tableButton);
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

    Button getTableButton() {
        return tableButton;
    }

    Button getStatisticsButton() {
        return statisticsButton;
    }

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
