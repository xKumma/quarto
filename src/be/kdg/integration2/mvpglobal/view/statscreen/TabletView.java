package be.kdg.integration2.mvpglobal.view.statscreen;

import be.kdg.integration2.mvpglobal.model.Tabledata;
import be.kdg.integration2.mvpglobal.view.base.BaseView;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import be.kdg.integration2.mvpglobal.model.Tabledata;

import java.sql.SQLException;
/**
 * Present in a tabular form main data from latest match played
 * winner
 * moves played by both bot and player separately
 * the average time taken to make a move for both bot and player
 */
public class TabletView extends BaseView {
    BorderPane pane;
    TableView<Tabledata.Data> table  ;
    Button menuButton;
    Button backButton;
    Label title;
    Label logoLabel;



    public TabletView()  {
        super();
    }

    @Override
    protected void initialiseNodes() throws SQLException {
        table = new TableView();
        pane=new BorderPane();
        title = new Label("game stats");
        logoLabel = new Label("\uD83C\uDD40uarto");
        backButton = new Button("BACK");
        menuButton = new Button("MENU");

    }

    @Override
    protected void layoutNodes() {
        table.setStyle("-fx-text-inner-color: black");
        table.getStyleClass().add("table");
        HBox buttons = new HBox(backButton, menuButton);
        buttons.setSpacing(15);
        buttons.setPadding(new Insets(15));
        setBottom(buttons);
        table.fixedCellSizeProperty().bind(table.widthProperty());
        table.setMinSize(50,50);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.setCenter(pane);
        pane.setCenter(table);
        BorderPane panepane = new BorderPane();
        title.setPadding(new Insets(20, 50, 50, 440));
        title.getStyleClass().add("title");
        logoLabel.setPadding(new Insets(50, 50, 50, 420));
        logoLabel.getStyleClass().add("logo");

        VBox box1 = new VBox(logoLabel, title);
        panepane.setCenter(box1);
        pane.setTop(panepane);

    }

    public Button getBackButton() {
        return backButton;
    }

    public Button getMenuButton() {
        return menuButton;
    }

    public TableView<Tabledata.Data> getTable() {
        return table;
    }




    public void setItems(ObservableList<Tabledata.Data> dataList) {
        table.setItems(dataList);
    }

    public void initializeTableColumns() {
        TableColumn<Tabledata.Data, String> nameCol = new TableColumn<>("Winner");
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        table.getColumns().add(nameCol);
        TableColumn<Tabledata.Data, Double> timeCol = new TableColumn<>("Time played");
        timeCol.setCellValueFactory(cellData -> cellData.getValue().timeProperty().asObject());
        table.getColumns().add(timeCol);
        TableColumn<Tabledata.Data, Double> mai = new TableColumn<>("ai mvs");
        mai.setCellValueFactory(cellData -> cellData.getValue().maiProperty().asObject());
        table.getColumns().add(mai);
        TableColumn<Tabledata.Data, Double> msp = new TableColumn<>("p mvs");
        msp.setCellValueFactory(cellData -> cellData.getValue().mpProperty().asObject());
        table.getColumns().add(msp);
        TableColumn<Tabledata.Data, Double> tai = new TableColumn<>("avg Tai(s) mv");
        tai.setCellValueFactory(cellData -> cellData.getValue().taiProperty().asObject());
        table.getColumns().add(tai);
        TableColumn<Tabledata.Data, Double> tp = new TableColumn<>("avg Tp(s) mv");
        tp.setCellValueFactory(cellData -> cellData.getValue().tpProperty().asObject());
        table.getColumns().add(tp);


    }


}
