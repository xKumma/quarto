package be.kdg.integration2.mvpglobal.view.mainmenu;

import be.kdg.integration2.mvpglobal.view.base.BaseView;
import be.kdg.integration2.mvpglobal.view.components.Header;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MainMenuView extends BaseView {

    Button startGameButton;
    Button rulesButton;
    Button leaderboardButton;
    Button quitButton;
    Rectangle rectangle;
    Header header;
    Button statisticsButton;
    Button tableButton;

    public MainMenuView() {
        super();
    }

    protected void initialiseNodes() {
        header = new Header();
        startGameButton = new Button("Start Game");
        rulesButton = new Button("Rules");
        leaderboardButton = new Button("Leaderboard");
        quitButton = new Button("Quit");
        statisticsButton = new Button("Statistics(temp)");
        tableButton = new Button("Table(temp)");
        this.rectangle = new Rectangle(200, 100);

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
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(3);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setRotate(45);

        setTop(header);
        VBox buttons = new VBox(new Label("Menu"),startGameButton, rulesButton, leaderboardButton, quitButton, statisticsButton, tableButton);
        buttons.setSpacing(4);
        buttons.setAlignment(Pos.CENTER);

        buttons.setMaxWidth(400);
        buttons.setMaxHeight(400);


        rectangle.widthProperty().bind(buttons.widthProperty().add(20));
        rectangle.heightProperty().bind(buttons.heightProperty().add(20));
        StackPane root = new StackPane(rectangle, buttons);
        StackPane.setAlignment(buttons, Pos.CENTER);
        StackPane.setAlignment(rectangle, Pos.CENTER);
        setCenter(root);


    }

    public Button getTableButton() {
        return tableButton;
    }

    public Button getStatisticsButton() {
        return statisticsButton;
    }

    public Header getHeader() {
        return header;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Button getQuitButton() {
        return quitButton;
    }

    public Button getLeaderboardButton() {
        return leaderboardButton;
    }

    public Button getRulesButton() {
        return rulesButton;
    }

    public Button getStartGameButton() {
        return startGameButton;
    }
}
