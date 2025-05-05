package be.kdg.integration2.mvpglobal.view.endscreen;

import be.kdg.integration2.mvpglobal.utility.dbconnection.DBManager;
import be.kdg.integration2.mvpglobal.view.base.BaseView;
import be.kdg.integration2.mvpglobal.view.components.Header;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class EndScreenView extends BaseView {

    private Header header;
    private Button menuButton;
    private Button tableButton;
    private Button graphButton;
    private Label winnerTextLabel;
    private String winnerName;


    public EndScreenView() {
        super();
    }

    protected void initialiseNodes() {
        Font font = new Font(16);
        header = new Header();
        menuButton = new Button("Menu");
        tableButton = new Button("Table");
        graphButton = new Button("Graph");
        try {
            winnerTextLabel = new Label(DBManager.getInstance().getWinnerName() + " won!");
        } catch (Exception e) {
            winnerTextLabel = new Label("Player x won!");
        }



    }

    protected void layoutNodes() {
        setTop(header);

        menuButton.setPrefSize(90, 20); // Width, Height
        tableButton.setPrefSize(90, 20);
        graphButton.setPrefSize(90, 20);

        //rectangle
        Rectangle rectangle = new Rectangle();
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(3);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setRotate(45);



        VBox textBox = new VBox(header, winnerTextLabel);
        textBox.setSpacing(4);
        textBox.setAlignment(Pos.CENTER);
        textBox.setMaxWidth(400);
        textBox.setMaxHeight(400);

        rectangle.widthProperty().bind(textBox.widthProperty().add(20));
        rectangle.heightProperty().bind(textBox.heightProperty().add(20));

        //center (rectangle + label)
        StackPane centerPane = new StackPane(rectangle, textBox);
        StackPane.setAlignment(winnerTextLabel, Pos.CENTER);
        StackPane.setAlignment(rectangle, Pos.CENTER);
        setCenter(centerPane);

        //buttons
        HBox hBoxButtons = new HBox(tableButton, graphButton);
        hBoxButtons.setAlignment(Pos.CENTER);
        hBoxButtons.setSpacing(20);
        VBox vBoxButtons = new VBox(hBoxButtons, menuButton);
        vBoxButtons.setAlignment(Pos.CENTER);
        vBoxButtons.setSpacing(20);


        setBottom(vBoxButtons);



        setPadding(new Insets(50));

    }

    public Label getWinnerTextLabel() {
        return winnerTextLabel;
    }

    public void setWinnerTextLabel(Label winnerTextLabel) {
        this.winnerTextLabel = winnerTextLabel;

    }

    public Button getGraphButton() {
        return graphButton;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public void setGraphButton(Button graphButton) {
        this.graphButton = graphButton;
    }

    public Button getTableButton() {
        return tableButton;
    }

    public void setTableButton(Button tableButton) {
        this.tableButton = tableButton;
    }

    public Button getMenuButton() {
        return menuButton;
    }

    public void setMenuButton(Button menuButton) {
        this.menuButton = menuButton;
    }

}
