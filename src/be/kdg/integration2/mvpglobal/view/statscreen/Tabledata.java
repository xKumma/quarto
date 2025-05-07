package be.kdg.integration2.mvpglobal.view.statscreen;

import be.kdg.integration2.mvpglobal.utility.dbconnection.DBManager;
import javafx.beans.property.*;
import javafx.scene.control.TableColumn;

import java.sql.SQLException;

public class Tabledata {


    protected StringProperty name = new SimpleStringProperty();
    protected DoubleProperty time = new SimpleDoubleProperty() ;
    protected DoubleProperty movesai = new SimpleDoubleProperty() ;
    protected DoubleProperty movesp = new SimpleDoubleProperty() ;
    protected DoubleProperty Tai = new SimpleDoubleProperty() ;
    protected DoubleProperty Tp = new SimpleDoubleProperty() ;
    protected DoubleProperty ScoreAI = new SimpleDoubleProperty() ;
    protected DoubleProperty ScoreP = new SimpleDoubleProperty() ;
    protected DBManager dbManager = DBManager.getInstance();


    public Tabledata() throws SQLException {
        getName();
        geTIME();
        getNMAI();
        getNMpl();
    }


    public void getName() throws SQLException {
        String namep = dbManager.getWinnerName();
        this.name.set(namep);
    }
    public void geTIME() throws SQLException {
        Double timep = dbManager.getTimeFIN(dbManager.getSessionID()) - dbManager.getTimeIN(dbManager.getSessionID());
        this.time.set(timep);
        System.out.println(time);

    }

    public void getNMAI() throws SQLException {
        Double movesaip= (double) dbManager.getNMovesAI(dbManager.getSessionID());
        this.movesai.set(movesaip);
        System.out.println(movesai);
    }
    public void getNMpl() throws SQLException {
        Double movespp= (double) dbManager.getNMovesPL(dbManager.getSessionID());
        this.movesp.set(movespp);
        System.out.println(movesp);

    }

}
