package be.kdg.integration2.mvpglobal.view.statscreen;

import be.kdg.integration2.mvpglobal.utility.dbconnection.DBManager;
import be.kdg.integration2.mvpglobal.view.base.BaseView;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.concurrent.*;
import java.util.ArrayList;
import java.util.List;

import static be.kdg.integration2.mvpglobal.view.statscreen.StatScreenPresenter.*;


public class StatScreenView extends BaseView {
    private Button menuButton;
    private Button backButton;
    private LineChart<Number, String> lineChart;
    private Rectangle rectangle1, rectangle2, rectangle3, rectangle4;
    private Pane chartContainer;
    private List<Double> serieC;
    private DBManager dbManager = DBManager.getInstance();


    public StatScreenView() {
        super();
        draw();
    }

    @Override
    protected void initialiseNodes() {
        menuButton = new Button("Menu");
        backButton = new Button("Back");
        lineChart = new LineChart<>(new NumberAxis(), new CategoryAxis());
        lineChart.setAnimated(false);
        lineChart.setMinSize(0, 0);
        lineChart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        serieC = new ArrayList<>();




    }

    @Override
    protected void layoutNodes() {
        // StackPane for responsiveness
        StackPane stack = new StackPane();

        // Overlay Pane for rectangles
        chartContainer = new Pane();
        chartContainer.setPickOnBounds(false); // Let mouse events pass through if needed
        chartContainer.prefWidthProperty().bind(lineChart.widthProperty());
        chartContainer.prefHeightProperty().bind(lineChart.heightProperty());

        stack.getChildren().addAll(lineChart, chartContainer);

        // Create rectangles
        rectangle1 = createRectangle();
        rectangle2 = createRectangle();
        rectangle3 = createRectangle();
        rectangle4 = createRectangle();

        chartContainer.getChildren().addAll(rectangle1, rectangle2, rectangle3, rectangle4);

        setCenter(stack);

        HBox buttons = new HBox(backButton, menuButton);
        buttons.setSpacing(15);
        buttons.setPadding(new Insets(15));
        setBottom(buttons);

        // Keep rectangles in sync with chart size
        lineChart.widthProperty().addListener((obs, o, n) -> updateAllRectangles());
        lineChart.heightProperty().addListener((obs, o, n) -> updateAllRectangles());
    }

    private Rectangle createRectangle() {
        Rectangle r = new Rectangle();
        r.setHeight(40);
        r.setStroke(Color.MEDIUMVIOLETRED);
        r.setFill(Color.TRANSPARENT);
        return r;
    }

    public List<Double> draw() {
        serieC.clear();
        for (XYChart.Data<Number, String> d : series3.getData()) {
            serieC.add(d.getXValue().doubleValue());
        }
        for (XYChart.Data<Number, String> d : series4.getData()) {
            serieC.add(d.getXValue().doubleValue());
        }

        lineChart.getData().addAll(series3, series4);

        Platform.runLater(() -> {
            styleSeries(series3, "green");
            styleSeries(series4, "blue");
            series1.setName("TimeP(s)");
            series2.setName("TimeAI(cs)");
            series3.setName("P Qs (s)");
            series4.setName("P Os (s)");
            series5.setName("AI Qs (cs)");
            series6.setName("AI Os (cs)");
            lineChart.setTitle("MOVES STATISTICS");


            updateAllRectangles();
        });

        return serieC;
    }

    private void styleSeries(XYChart.Series<Number, String> series, String color) {
        if (series.getNode() != null) {
            series.getNode().setStyle("-fx-stroke: transparent;");
            for (XYChart.Data<Number, String> data : series.getData()) {
                data.getNode().setStyle("-fx-background-color: " + color + ", white;");
            }
        }
    }

    private void updateAllRectangles() {
        if (series3.getData().size() >= 3 && series4.getData().size() >= 3) {
            updateRectangle(rectangle1, series3.getData().get(0), series3.getData().get(1), -12);
            updateRectangle(rectangle2, series3.getData().get(1), series3.getData().get(2), -12);
            updateRectangle(rectangle3, series4.getData().get(0), series4.getData().get(1), -12);
            updateRectangle(rectangle4, series4.getData().get(1), series4.getData().get(2), -12);
        }
    }

    private void updateRectangle(Rectangle rect, XYChart.Data<Number, String> start, XYChart.Data<Number, String> end, int offsetY) {
        if (start.getNode() == null || end.getNode() == null) return;

        Platform.runLater(() -> {
            Bounds startScene = start.getNode().localToScene(start.getNode().getBoundsInLocal());
            Bounds endScene = end.getNode().localToScene(end.getNode().getBoundsInLocal());

            Bounds startLocal = chartContainer.sceneToLocal(startScene);
            Bounds endLocal = chartContainer.sceneToLocal(endScene);

            double x1 = startLocal.getMinX() + startLocal.getWidth() / 2;
            double x2 = endLocal.getMinX() + endLocal.getWidth() / 2;
            double centerY = (startLocal.getMinY() + endLocal.getMinY()) / 2 + offsetY;

            rect.setLayoutX(Math.min(x1, x2));
            rect.setLayoutY(centerY);
            rect.setWidth(Math.abs(x2 - x1));
        });
    }

    public Button getMenuButton() {
        return menuButton;
    }
    private void hideSeriesFromLegend(XYChart.Series<Number, String> series) {
        Platform.runLater(() -> {
            Node chartLegend = lineChart.lookup(".chart-legend");
            if (chartLegend != null) {
                for (Node legendItem : chartLegend.lookupAll(".chart-legend-item")) {
                    if (legendItem instanceof Label && ((Label) legendItem).getText().equals(series.getName())) {
                        legendItem.setVisible(false);
                        legendItem.setManaged(false);

                    }
                }
            }
        });
    }

    public Button getBackButton() {
        return backButton;
    }

    public void setBackButton(Button backButton) {
        this.backButton = backButton;
    }

    public LineChart<Number, String> getLineChart() {
        return lineChart;
    }
   }