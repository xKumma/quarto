package be.kdg.integration2.mvpglobal.view.leaderboardscreen;

import be.kdg.integration2.mvpglobal.view.UISettings;
import be.kdg.integration2.mvpglobal.view.base.BaseView;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LeaderboardScreenView extends BaseView {
    private UISettings uiSettings;
    private Label headerLabel;
    private Label logoLabel;
    private Button mainButton;
    private MenuItem winsMI;
    private MenuItem lossMI;
    private MenuItem avgMMI;
    private MenuItem avgTMI;
    private MenuItem ascMI;
    private MenuItem descMI;
    private MenuButton filterMB;

    public LeaderboardScreenView() {
        super();
    }

    public void initialiseNodes () {
        this.headerLabel = new Label("Leaderboard");
        this.logoLabel = new Label("\uD83C\uDD40uarto");
        this.mainButton = new Button("Mainscreen");
        this.winsMI = new MenuItem("Wins");
        this.lossMI = new MenuItem("Losses");
        this.avgMMI = new MenuItem("Average Moves");
        this.avgTMI = new MenuItem("Average Time");
        this.ascMI = new MenuItem("Ascending");
        this.descMI = new MenuItem("Descending");
        this.filterMB = new MenuButton("Filter By:");
    }

    public void layoutNodes () {
        TableView<Object> leaderboardTable = new TableView<>();
        TableColumn<Object,String> nameColumn = new TableColumn<>("Name");
        TableColumn<Object,Integer> rankColumn = new TableColumn<>("Rank");
        TableColumn<Object,Integer> gameCountColumn = new TableColumn<>("Games Played");
        TableColumn<Object,Integer> winsColumn = new TableColumn<>("Wins");
        TableColumn<Object,Integer> lossesColumn = new TableColumn<>("Losses");
        TableColumn<Object,Integer> winRateColumn = new TableColumn<>("Winrate");
        TableColumn<Object,Integer> avgMovesColumn = new TableColumn<>("Average Moves");
        TableColumn<Object,Integer> avgTimeColumn = new TableColumn<>("Average Time");

        leaderboardTable.getColumns().addAll(rankColumn,nameColumn,gameCountColumn,winsColumn,lossesColumn,winRateColumn,avgMovesColumn,avgTimeColumn);
        leaderboardTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        logoLabel.setStyle("-fx-font-size: 80px");
        headerLabel.setStyle("-fx-font-size: 30px");
        filterMB.getItems().addAll(winsMI, lossMI, avgMMI,avgTMI, ascMI, descMI);
        HBox box0 = new HBox(30,mainButton,filterMB);
        box0.setAlignment(Pos.CENTER);
        VBox box1 = new VBox(10,logoLabel, headerLabel,leaderboardTable, box0);
        box1.setAlignment(Pos.CENTER);
        setCenter(box1);
    }
    public Button getMainButton() {return mainButton;}
}

