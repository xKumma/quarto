package be.kdg.integration2.mvpglobal.view.endscreen;

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
import javafx.scene.shape.Rectangle;

/**
 * EndScreenView class represents the end screen of the application.
 */
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
        header = new Header();
        menuButton = new Button("Menu");
        tableButton = new Button("Table");
        graphButton = new Button("Graph");
        winnerTextLabel = new Label();
    }

    protected void layoutNodes() {

        header.setStyle(
                "-fx-padding: 0px;"
        );
        //setTop(header);

        menuButton.getStyleClass().addAll("menu-button");
        menuButton.setPrefSize(105,20);

        //rectangle
        Rectangle rectangle = new Rectangle();
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(3);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setRotate(45);

        //buttons
        HBox hBoxButtons = new HBox(tableButton, graphButton);
        hBoxButtons.getChildren().forEach(node -> {
            node.getStyleClass().addAll("menu-button");
            ((Button)node).setPrefSize(105,20);
        });
        hBoxButtons.setAlignment(Pos.CENTER);
        hBoxButtons.setSpacing(20);
        VBox vBoxButtons = new VBox(hBoxButtons, menuButton);
        vBoxButtons.setAlignment(Pos.CENTER);
        vBoxButtons.setSpacing(20);


        VBox textBox = new VBox(header, winnerTextLabel, vBoxButtons);
        winnerTextLabel.setStyle("-fx-font-size: 30px;");
        textBox.setSpacing(80);
        textBox.setAlignment(Pos.CENTER);
        textBox.setMaxWidth(400);
        textBox.setMaxHeight(400);

        rectangle.widthProperty().bind(textBox.widthProperty().add(60));
        rectangle.heightProperty().bind(textBox.heightProperty().add(60));


        //center (rectangle + label)
        StackPane centerPane = new StackPane(rectangle, textBox);
        StackPane.setAlignment(winnerTextLabel, Pos.CENTER);
        StackPane.setAlignment(rectangle, Pos.CENTER);
        setCenter(centerPane);




        //setBottom(vBoxButtons);



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
