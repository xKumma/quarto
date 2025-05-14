package be.kdg.integration2.mvpglobal.view.statscreen;

import be.kdg.integration2.mvpglobal.model.Screen;
import be.kdg.integration2.mvpglobal.model.Tabledata;
import be.kdg.integration2.mvpglobal.utility.Router;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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


    public TablePresenter( TabletView view , Tabledata model) {
        super( view , model  );

        try {
            updateval();
        } catch (Exception e) {
            System.err.println("Error updating statistics: " + e.getMessage());
        }

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




