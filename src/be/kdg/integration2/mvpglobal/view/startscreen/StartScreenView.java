package be.kdg.integration2.mvpglobal.view.startscreen;

import be.kdg.integration2.mvpglobal.view.base.BaseView;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public final class StartScreenView extends BaseView {

    private Label timeDisplay;
    private ProgressBar timeProgress;
    private StartScreenTransition trans;
    private Circle circle;
    private Rectangle rectangle;
    private Rectangle rectangle1;
    private Rectangle rectangle2;
    private Rectangle rectangle3;

    private StackPane graphic = new StackPane();
    private Label titolo;



    public StartScreenView() {
        super();
        animate();
    }


    protected void initialiseNodes() {
        this.timeDisplay = new Label("Loading: 0.0");
        this.timeProgress = new ProgressBar();
        circle = new Circle();
        rectangle = new Rectangle();

        rectangle1 = new Rectangle();
        rectangle2 = new Rectangle();
        rectangle3 = new Rectangle();
        titolo = new Label();
        this.titolo.setStyle("   -fx-font-size: 20pt  ;  -fx-text-fill: white ");

    }


    protected void layoutNodes() {
        //color
        this.setStyle("    -fx-background-color: radial-gradient(focus-distance 0% , center 50% 50% , radius 55% , #D4D4D4, black  );");
        circle.setStroke(Color.WHITE);
        circle.setFill(Color.TRANSPARENT);
        circle.setStyle("-fx-border-width: 5px");
        rectangle.setStroke(Color.WHITE);
        rectangle1.setStroke(Color.WHITE);
        rectangle2.setStroke(Color.WHITE);
        rectangle3.setStroke(Color.WHITE);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle1.setFill(Color.TRANSPARENT);
        rectangle1.setRotate(45);
        rectangle2.setFill(Color.TRANSPARENT);
        rectangle2.setRotate(110);
        rectangle3.setFill(Color.TRANSPARENT);
        rectangle3.setRotate(155);
        //geometry
        circle.radiusProperty().bind(this.heightProperty().divide(2.45));
        rectangle.heightProperty().bind(this.heightProperty().divide(1.25*Math.sqrt(2)));
        rectangle.widthProperty().bind(this.widthProperty().divide(1.25*Math.sqrt(2)));
        rectangle1.heightProperty().bind(this.heightProperty().divide(1.25*Math.sqrt(2)));
        rectangle2.heightProperty().bind(this.heightProperty().divide(1.25*Math.sqrt(2)));
        rectangle3.heightProperty().bind(this.heightProperty().divide(1.25*Math.sqrt(2)));
        rectangle1.widthProperty().bind(this.widthProperty().divide(1.25*Math.sqrt(2)));
        rectangle2.widthProperty().bind(this.widthProperty().divide(1.25*Math.sqrt(2)));
        rectangle3.widthProperty().bind(this.widthProperty().divide(1.25*Math.sqrt(2)));

        BorderPane progressPane = new BorderPane();
        graphic = new StackPane();
        progressPane.getChildren().addAll(graphic);
        titolo.setText("     \uD83C\uDD40UARTO     " );
        this.titolo.setId("titolo");
        this.titolo.setStyle("   -fx-font-size: 20pt  ;  -fx-text-fill: white ");

        // this.getChildren().add(titolo);
        this.setTop(titolo);



        this.setCenter(graphic);


        graphic.getChildren().addAll( circle, rectangle, rectangle1, rectangle2, rectangle3,titolo );

        progressPane.setRight(this.timeProgress);
        progressPane.setLeft(this.timeDisplay);
        BorderPane.setMargin(this.timeDisplay, new Insets(uiSettings.getInsetsMargin()));
        BorderPane.setMargin(this.timeProgress, new Insets(uiSettings.getInsetsMargin()));
        this.setBottom(progressPane);

        setMinHeight(500);
        setMinWidth(500);
        setMaxHeight(1000);
        setMaxWidth(1900);


    }


    Label getTimeDisplay () {return (timeDisplay);}

    ProgressBar getTimeProgress () {return (timeProgress);}

    StartScreenTransition getTransition() {return trans;}

    Circle getCircle() {
        return circle;
    }

    Rectangle getRectangle() {
        return rectangle;
    }

    Rectangle getRectangle1() {
        return rectangle1;
    }

    Rectangle getRectangle2() {
        return rectangle2;
    }

    Rectangle getRectangle3() {
        return rectangle3;
    }

    Label getTitolo() {
        return titolo;
    }

    private void animate() {
        trans = new StartScreenTransition(this,5);
        trans.sqaretransition(rectangle , rectangle1 , rectangle2 , rectangle3);
        trans.play();
    }





}
