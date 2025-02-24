package be.kdg.integration2.mvpglobal.view.startscreen;

import be.kdg.integration2.mvpglobal.view.UISettings;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.MalformedURLException;
import java.nio.file.Files;

public class StartScreenView extends BorderPane  {

    private UISettings uiSettings;
    private Label timeDisplay;
    private ProgressBar timeProgress;
    private StartScreenTransition trans;
    private Circle circle;
    private Rectangle rectangle;
    private Rectangle rectangle1;
    private Rectangle rectangle2;
    private Rectangle rectangle3;

    private StackPane graphic = new StackPane();
    Label titolo;


    public StartScreenView(UISettings uiSettings) {
        this.uiSettings = uiSettings;
        initialiseNodes();
        layoutNodes();
        animate();
    }

    private void initialiseNodes() {
        this.timeDisplay = new Label("Loading: 0.0");
        this.timeProgress = new ProgressBar();
        circle = new Circle();
        rectangle = new Rectangle();
        rectangle1 = new Rectangle();
        rectangle2 = new Rectangle();
        rectangle3 = new Rectangle();
        titolo = new Label();
    }


    private void layoutNodes() {
        //color
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        rectangle1.setStroke(Color.BLACK);
        rectangle2.setStroke(Color.BLACK);
        rectangle3.setStroke(Color.BLACK);

        rectangle.setFill(Color.TRANSPARENT);
        rectangle1.setFill(Color.TRANSPARENT);
        rectangle1.setRotate(45);
        rectangle2.setFill(Color.TRANSPARENT);
        rectangle2.setRotate(112);
        rectangle3.setFill(Color.TRANSPARENT);
        rectangle3.setRotate(72);
        //geometry
        circle.radiusProperty().bind(this.heightProperty().divide(3.80));
        rectangle.heightProperty().bind(this.heightProperty().divide(2*Math.sqrt(2)));
        rectangle.widthProperty().bind(this.widthProperty().divide(2*Math.sqrt(2)));
        rectangle1.heightProperty().bind(this.heightProperty().divide(2*Math.sqrt(2)));
        rectangle2.heightProperty().bind(this.heightProperty().divide(2*Math.sqrt(2)));
        rectangle3.heightProperty().bind(this.heightProperty().divide(2*Math.sqrt(2)));
        rectangle1.widthProperty().bind(this.widthProperty().divide(2*Math.sqrt(2)));
        rectangle2.widthProperty().bind(this.widthProperty().divide(2*Math.sqrt(2)));
        rectangle3.widthProperty().bind(this.widthProperty().divide(2*Math.sqrt(2)));

        BorderPane progressPane = new BorderPane();
        progressPane.getChildren().add(graphic);
        titolo.setText("     QUARTO     " );

        // this.getChildren().add(titolo);
        this.setTop(titolo);


        this.setCenter(graphic);

        graphic.getChildren().addAll(circle, rectangle, rectangle1, rectangle2, rectangle3,titolo);
        progressPane.setRight(this.timeProgress);
        progressPane.setLeft(this.timeDisplay);
        BorderPane.setMargin(this.timeDisplay, new Insets(uiSettings.getInsetsMargin()));
        BorderPane.setMargin(this.timeProgress, new Insets(uiSettings.getInsetsMargin()));
        /*
        ImageView centralImage;
        if (Files.exists(uiSettings.getStartScreenImagePath())) {
           try {
                centralImage = new ImageView(new Image(uiSettings.getStartScreenImagePath().toUri().toURL().toString()));
                centralImage.setPreserveRatio(true);
                centralImage.setFitHeight(ImageSize);
                centralImage.setFitWidth(ImageSize);
                centralImage.setSmooth(true);
                this.setCenter(centralImage);
            }
            catch (MalformedURLException ex) {
                // do nothing, if toURL-conversion fails, program can continue
            }
        } else { // do nothing, if StartScreenImage is not available, program can continue
        }

         */
        this.setBottom(progressPane);




    }


    Label getTimeDisplay () {return (timeDisplay);}

    ProgressBar getTimeProgress () {return (timeProgress);}

    StartScreenTransition getTransition() {return trans;}

    private void animate() {
        trans = new StartScreenTransition(this,3);
        trans.play();
    }



}
