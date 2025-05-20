package be.kdg.integration2.mvpglobal.model.dataobjects;

import be.kdg.integration2.mvpglobal.utility.dbconnection.DBManager;
import javafx.beans.property.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * model class related to the table statistic at the end of a match:
 * retrives data from the database assigning them to the attributes that will be filled into the tableview
 * data provided:
 * winner
 * moves played by both bot and player separately
 * the average time taken to make a move for both bot and player
 */
public class Tabledata {
    /**
     * Inner Class that provides the attributes needed to fill the Tableview columns
     *
     */
    public static class Data{
        private StringProperty  name  = new SimpleStringProperty();
        private DoubleProperty time = new SimpleDoubleProperty() ;
        private DoubleProperty movesai = new SimpleDoubleProperty() ;
        private DoubleProperty movesp = new SimpleDoubleProperty() ;
        private DoubleProperty Tai = new SimpleDoubleProperty() ;
        private DoubleProperty Tp = new SimpleDoubleProperty() ;


        public void setName(String name) {
            this.name.set(name);
        }
        public void setTime(Double time) {
            this.time.set(time);
        }
        public void setMovesai(Double movesai) {
            this.movesai.set(movesai);
        }
        public void setMovesp(Double movesp) {
            this.movesp.set(movesp);
        }
        public void setTai(Double Tai) {
            this.Tai.set(Tai);
        }
        public void setTp(Double Tp) {
            this.Tp.set(Tp);
        }

        public StringProperty nameProperty() {
            return name;
        }
        public DoubleProperty timeProperty() {
            return time;
        }

        public DoubleProperty maiProperty() {
            return movesai;
        }
        public DoubleProperty mpProperty() {
            return movesp;
        }
        public DoubleProperty taiProperty() {
            return Tai;
        }
        public DoubleProperty tpProperty() {
            return Tp;
        }


        @Override
        public String toString() {
            return "Data{name='" + name.get() + "', score=" + time.get() + "}";

        }

    }

    private Data data = new Data();
    protected DBManager dbManager = DBManager.getInstance();
    Double movesaip;

    public Tabledata() {
        try {
            getName();
            geTIME();
            getNMAI();
            getNMpl();
            getTai();
            getTPL();
        } catch (Exception e) {
            System.out.println(e);
            System.err.println("Error connecting to DB, not able to retrieve data");
        }
    }


    public Data getName() throws SQLException {
        String nameFromDB = dbManager.getWinnerName(); // get name from DB
        data.setName(nameFromDB);                      // set it in the object
        return data;

    }

    public Data geTIME() throws SQLException {
        Double timep = dbManager.getTimeFIN(dbManager.getSessionID()) - dbManager.getTimeIN(dbManager.getSessionID());
         data.setTime(truncateTo2Decimals(timep));
        System.out.println(truncateTo2Decimals(timep));
        return data;

    }

    public Data getNMAI() throws SQLException {
        movesaip= (double) dbManager.getNMovesAI(dbManager.getSessionID());
        data.setMovesai(movesaip);
        System.out.println(truncateTo2Decimals(movesaip));
        return data;
    }
    public Data getNMpl() throws SQLException {
        Double movespp= (double) dbManager.getNMovesPL(dbManager.getSessionID());
        data.setMovesp(truncateTo2Decimals(movespp));
        System.out.println(truncateTo2Decimals(movespp));
        return data;

    }

    public Data getTai() throws SQLException {
        List<Double> taiprovvisory = new ArrayList<>();
        taiprovvisory=dbManager.getTMAIf(dbManager.getSessionID());

        double sum = 0;
        for (double value : taiprovvisory) {
            sum += value;
            System.out.println(value);
        }

        double average = taiprovvisory.isEmpty() ? 0 : sum / taiprovvisory.size();
        System.out.println("Media: " + average);
        data.setTai(Math.floor(average * 10000) / 10000.0);
        return data;
    }

    public Data getTPL() throws SQLException {
        List<Double> tmpprovvisory = new ArrayList<>();
        tmpprovvisory=dbManager.getTMPs(dbManager.getSessionID());

        double sum = 0;
        for (double value : tmpprovvisory) {
            sum += value;
            System.out.println(value);
        }

        double average = tmpprovvisory.isEmpty() ? 0 : sum / tmpprovvisory.size();
        System.out.println("Media: " + average);
        data.setTp(truncateTo2Decimals(average));
        return data;
    }
    public static double truncateTo2Decimals(double value) {
        return Math.floor(value * 100) / 100.0;
    }

}
