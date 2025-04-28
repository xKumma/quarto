package be.kdg.integration2.mvpglobal.view.leaderboardscreen;

import be.kdg.integration2.mvpglobal.model.LeaderboardData;
import be.kdg.integration2.mvpglobal.view.UISettings;
import be.kdg.integration2.mvpglobal.view.base.BaseView;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
        this.mainButton = new Button("Menu");
        this.winsMI = new MenuItem("Wins");
        this.lossMI = new MenuItem("Losses");
        this.avgMMI = new MenuItem("Average Moves");
        this.avgTMI = new MenuItem("Average Time");
        this.ascMI = new MenuItem("Ascending");
        this.descMI = new MenuItem("Descending");
        this.filterMB = new MenuButton("Filter By:");
    }

    public void layoutNodes () {
        TableView<LeaderboardData> leaderboardTable = new TableView<>();
        TableColumn<LeaderboardData,String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<LeaderboardData,Integer> rankColumn = new TableColumn<>("Rank");
        rankColumn.setCellFactory(col -> new TableCell<LeaderboardData, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.valueOf(getIndex() + 1));
                }
            }
        });
        TableColumn<LeaderboardData,Integer> gameCountColumn = new TableColumn<>("Games Played");
            gameCountColumn.setCellValueFactory(new PropertyValueFactory<>("gamesPlayed"));
        TableColumn<LeaderboardData,Integer> winsColumn = new TableColumn<>("Wins");
            winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));
        TableColumn<LeaderboardData,Integer> lossesColumn = new TableColumn<>("Losses");
            lossesColumn.setCellValueFactory(new PropertyValueFactory<>("losses"));
        TableColumn<LeaderboardData,Double> winRateColumn = new TableColumn<>("Winrate");
            winRateColumn.setCellValueFactory(new PropertyValueFactory<>("winRate"));
        TableColumn<LeaderboardData,Double> avgMovesColumn = new TableColumn<>("Average Moves");
            avgMovesColumn.setCellValueFactory(new PropertyValueFactory<>("averageMoves"));
        TableColumn<LeaderboardData,Double> avgTimeColumn = new TableColumn<>("Average Time");
            avgTimeColumn.setCellValueFactory(new PropertyValueFactory<>("averageTime"));

        leaderboardTable.getColumns().addAll(rankColumn,nameColumn,gameCountColumn,winsColumn,lossesColumn,winRateColumn,avgMovesColumn,avgTimeColumn);
        leaderboardTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        logoLabel.setStyle("-fx-font-size: 80px");
        headerLabel.setStyle("-fx-font-size: 30px");
        filterMB.getItems().addAll(winsMI, lossMI, avgMMI,avgTMI, ascMI, descMI);
        HBox box0 = new HBox(30,mainButton/*,filterMB*/);
        box0.setAlignment(Pos.CENTER);
        VBox box1 = new VBox(10,logoLabel, headerLabel,leaderboardTable, box0);
        box1.setAlignment(Pos.CENTER);
        setCenter(box1);
        leaderboardTable.setItems(LeaderboardData.LeaderboardData);
    }
    Button getMainButton() {return mainButton;}

    public MenuItem getWinsMI() {
        return winsMI;
    }

    public MenuItem getLossMI() {
        return lossMI;
    }

    public MenuItem getAvgMMI() {
        return avgMMI;
    }

    public MenuItem getAvgTMI() {
        return avgTMI;
    }

    public MenuItem getAscMI() {
        return ascMI;
    }

    public MenuItem getDescMI() {
        return descMI;
    }
}

