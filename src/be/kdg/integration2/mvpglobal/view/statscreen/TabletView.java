package be.kdg.integration2.mvpglobal.view.statscreen;

import be.kdg.integration2.mvpglobal.model.Statistics;
import be.kdg.integration2.mvpglobal.view.base.BaseView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TabletView extends BaseView {

    TableView table;
    TableColumn name;
    TableColumn score;
    TableColumn time ;




    public TabletView()  {
        super();
    }

    @Override
    protected void initialiseNodes() {
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

    @Override
    protected void layoutNodes() {

    }


}
