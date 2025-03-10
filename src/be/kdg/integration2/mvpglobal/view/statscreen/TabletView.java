package be.kdg.integration2.mvpglobal.view.statscreen;

import be.kdg.integration2.mvpglobal.model.Statistics;
import be.kdg.integration2.mvpglobal.view.UISettings;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

public class TabletView extends BorderPane {

    TableView table;
    TableColumn name;
    TableColumn score;
    TableColumn time ;




    public TabletView(UISettings settings) throws SQLException {
        table= new TableView<Statistics>();
        name = new TableColumn<Statistics , String>("Name");

        score = new TableColumn<Statistics , Double>("score");
        time = new TableColumn<Statistics , Double >("time");
        name.setCellValueFactory(new PropertyValueFactory<Statistics , String>("firstname"));
        score.setCellValueFactory(new PropertyValueFactory<Statistics , Double>("score"));
        time.setCellValueFactory(new PropertyValueFactory<Statistics , Double>("time"));
        table.getColumns().addAll(name, score, time);
        table.getItems().addAll(new Statistics());
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setCenter(table);


    }




}
