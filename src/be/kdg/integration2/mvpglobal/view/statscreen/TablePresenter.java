package be.kdg.integration2.mvpglobal.view.statscreen;

import be.kdg.integration2.mvpglobal.model.BaseModel;
import be.kdg.integration2.mvpglobal.model.Statistics;
import be.kdg.integration2.mvpglobal.model.Tabledata;
import be.kdg.integration2.mvpglobal.utility.Router;
import be.kdg.integration2.mvpglobal.model.Screen;
import be.kdg.integration2.mvpglobal.utility.dbconnection.DBManager;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

/**
 * fills the data from the class model dataTable and give them to the viewTable
 * data provided:
 * winner
 * moves played by both bot and player separately
 * the average time taken to make a move for both bot and player
 */
public class TablePresenter extends BasePresenter<TabletView, Tabledata> {


    ObservableList<Tabledata.Data> dataList = FXCollections.observableArrayList();


    public TablePresenter( TabletView view , Tabledata model) throws SQLException {
        super( view , model  );





        updateval();

    }

    public void updateval() throws SQLException {
        view.initializeTableColumns();
        dataList.add(model.getName());


        view.setItems(dataList);

    }






    protected void addEventHandlers() {

        view.getMenuButton().setOnAction(e -> goToMenu());
        view.getBackButton().setOnAction(e -> goToWin());
    }

    private void goToMenu() {
        Router.getInstance().goTo(Screen.MAIN_MENU, null);
    }

    private void goToWin() { Router.getInstance().goTo(Screen.END_SCREEN, null); }



}




