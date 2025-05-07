package be.kdg.integration2.mvpglobal.view.statscreen;

import be.kdg.integration2.mvpglobal.model.Statistics;
import be.kdg.integration2.mvpglobal.utility.dbconnection.DBManager;
import be.kdg.integration2.mvpglobal.view.base.BaseView;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class TabletView extends BaseView {
    Tabledata data;
    BorderPane pane;
    TableView<Tabledata> table ;
    TableColumn <Tabledata, String>name;
    TableColumn <Tabledata, Double>time ;
    Button menuButton;
    Button backButton;
    TableColumn<Tabledata, Double> movesai;
    TableColumn<Tabledata, Double> movesp;
    TableColumn<Tabledata, Double> Tai;
    TableColumn<Tabledata, Double> Tp;
    //TableColumn<Tabledata, Double> ScoreAI;
    //TableColumn<Tabledata, Double> ScoreP;


    public TabletView()  {
        super();
    }

    @Override
    protected void initialiseNodes() throws SQLException {
        table = new TableView();
        pane=new BorderPane();
        Label title = new Label("game stats");
        Label logoLabel = new Label("\uD83C\uDD40uarto");
        name = new TableColumn<>("winner");
        menuButton = new Button("Menu");
        backButton = new Button("Back");
        time = new TableColumn<>("time played (s)");
        movesai = new TableColumn<>("moves ai");
        movesp = new TableColumn<>("moves p");
        Tai = new TableColumn<>("T(s) mvs ai ");
        Tp = new TableColumn<>("T(s) mvs p");
        //ScoreAI= new TableColumn<>("score ai ");
        //ScoreP= new TableColumn<>("score P");
        table.getColumns().addAll(name, time ,movesai,movesp,Tai,Tp);
        table.setStyle("-fx-text-inner-color: black");
        table.getStyleClass().add("table");

        data = new Tabledata();
       // name.setCellValueFactory (new PropertyValueFactory<>("name"));
        name.setCellValueFactory(cellData -> cellData.getValue().name);
        time.setCellValueFactory(cellData-> cellData.getValue().time.asObject());
        movesai.setCellValueFactory(cellData->cellData.getValue().movesai.asObject());
        movesp.setCellValueFactory(cellData->cellData.getValue().movesp.asObject());
        Tai.setCellValueFactory(cellData->cellData.getValue().Tai.asObject());
        Tp.setCellValueFactory(cellData->cellData.getValue().Tp.asObject());

        table.getItems().addAll(data);
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
