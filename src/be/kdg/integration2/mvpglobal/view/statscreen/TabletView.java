package be.kdg.integration2.mvpglobal.view.statscreen;

import be.kdg.integration2.mvpglobal.model.Statistics;
import be.kdg.integration2.mvpglobal.view.base.BaseView;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class TabletView extends BaseView {

    TableView table;
    TableColumn name;
    TableColumn score;
    TableColumn time ;
    Button menuButton;
    Button backButton;




    public TabletView()  {
        super();
    }

    @Override
    protected void initialiseNodes() {
        table= new TableView<Statistics>();
        name = new TableColumn<Statistics , String>("Name");
        menuButton = new Button("Menu");
        backButton = new Button("Back");

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
        HBox buttons = new HBox(backButton, menuButton);
        buttons.setSpacing(15);
        buttons.setPadding(new Insets(15));
        setBottom(buttons);
    }

    public Button getBackButton() {
        return backButton;
    }

    public Button getMenuButton() {
        return menuButton;
    }
}
