package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.utility.dbconnection.DBManager;
import javafx.beans.property.*;

import java.sql.SQLException;

public class Tabledata {
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


    public Tabledata() throws SQLException {
        getName();
        geTIME();
        getNMAI();
        getNMpl();
        getTai();
        getTPL();
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
        Double movesaip= (double) dbManager.getNMovesAI(dbManager.getSessionID());
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
        Double taiprovvisory =  (dbManager.getTMAIf(dbManager.getSessionID())- dbManager.getTMAIs(dbManager.getSessionID()))/ dbManager.getNMovesAI(dbManager.getSessionID());
        data.setTai(truncateTo2Decimals(taiprovvisory));
        System.out.println(taiprovvisory);
        return data;
    }

    public Data getTPL() throws SQLException {
        Double tplprovvisory =  (dbManager.getTMPf(dbManager.getSessionID())- dbManager.getTMPs(dbManager.getSessionID()))/ dbManager.getNMovesPL(dbManager.getSessionID());
        data.setTp(truncateTo2Decimals(tplprovvisory));
        System.out.println(tplprovvisory);
        return data;
    }

    public static double truncateTo2Decimals(double value) {
        return Math.floor(value * 100) / 100.0;
    }

}
