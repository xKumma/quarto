package be.kdg.integration2.mvpglobal.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LeaderboardData {
    private SimpleStringProperty name;
    private SimpleIntegerProperty rank; // test, will be added automatically
    private SimpleIntegerProperty gamesPlayed;
    private SimpleIntegerProperty wins;
    private SimpleIntegerProperty losses;
    private SimpleDoubleProperty winRate;
    private SimpleDoubleProperty averageMoves;
    private SimpleDoubleProperty averageTime;

    public LeaderboardData(String name, int gamesPlayed, int wins, int losses, double averageMoves, double averageTime) {
        this.name = new SimpleStringProperty(name);
        this.rank = new SimpleIntegerProperty (0);
        this.gamesPlayed = new SimpleIntegerProperty(gamesPlayed);
        this.wins = new SimpleIntegerProperty (wins);
        this.losses = new SimpleIntegerProperty(losses);
        this.winRate = new SimpleDoubleProperty((double)wins / (double) gamesPlayed *100);
        this.averageMoves = new SimpleDoubleProperty(averageMoves);
        this.averageTime = new SimpleDoubleProperty(averageTime);
    }

    public static ObservableList<LeaderboardData> LeaderboardData= FXCollections.observableArrayList();

    public SimpleStringProperty nameProperty() {return name;}

    public SimpleIntegerProperty gamesPlayedProperty() {return gamesPlayed;}

    public SimpleIntegerProperty winsProperty() {return wins;}

    public SimpleIntegerProperty lossesProperty() {return losses;}

    public SimpleDoubleProperty avgMovesProperty() {return averageMoves;}

    public SimpleDoubleProperty avgTimeProperty() {return averageTime;}

    public String getName() {
        return name.get();}

    public int getRank() {
        return rank.get();}

    public int getGamesPlayed() {
        return gamesPlayed.get();
    }

    public int getWins() {
        return wins.get();
    }

    public int getLosses() {
        return losses.get();
    }

    public double getWinRate() {
        return winRate.get();
    }

    public double getAverageMoves() {
        return averageMoves.get();
    }

    public double getAverageTime() {
        return averageTime.get();
    }

}
