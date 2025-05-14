package be.kdg.integration2.mvpglobal.view.statscreen;

import be.kdg.integration2.mvpglobal.utility.Router;
import be.kdg.integration2.mvpglobal.model.Screen;
import be.kdg.integration2.mvpglobal.model.Statistics;
import be.kdg.integration2.mvpglobal.utility.dbconnection.DBManager;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;
import javafx.scene.chart.XYChart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatScreenPresenter extends BasePresenter<StatScreenView, Statistics> {


    protected double[] values1;
    int size1;
    int size3;
    int size5;
    int size6;
    protected double[] values2;
    int size2;
    protected double[] xAxis;
    protected String player = "player";
    protected String player2 = "ai";
    protected List<Double> val1;
    protected List<Double> val2;
    protected List<Integer> moves;
    protected List<Double> statA;
    protected List<Double> statB;
    protected static XYChart.Series<Number, String> series6;
    protected static XYChart.Series<Number, String> series1 = new XYChart.Series<>();
    protected static  XYChart.Series<Number, String> series2 = new XYChart.Series<>();
    protected static XYChart.Series<Number, String> series3 = new XYChart.Series<>();
    protected static  XYChart.Series<Number, String> series4 = new XYChart.Series<>();
    protected static  XYChart.Series<Number, String> series5 = new XYChart.Series<>();
    private DBManager dbManager = DBManager.getInstance();



    protected void addEventHandlers() {

        view.getMenuButton().setOnAction(e -> goToMenu());
        view.getBackButton().setOnAction(e -> goToWin());
    }

    private void goToMenu() {
        Router.getInstance().goTo(Screen.MAIN_MENU, null);
    }

    private void goToWin() {
        Router.getInstance().goTo(Screen.END_SCREEN, null);
    }


    public StatScreenPresenter(StatScreenView view, Statistics model) {
        super(view, model);

        if (this.model == null) {
            System.out.println("model is null");
            return;
        }


        //arrayx();
        updateVIew();
    }

    public void init() {
        super.init();

        try {
            model.launch();
            player = dbManager.getUserNameFromSession(dbManager.getSessionID());

        } catch (SQLException e) {
            System.err.println("Database connection is not initialized.");
            return;
        }


        size1 = model.getTime1().length;
        values1 = new double[size1];
        size2 = model.getTime2().length;
        values2 = new double[size2];
        xAxis = new double[size2];
        val1 = new ArrayList<>();
        val2 = new ArrayList<>();
        size3= model.getStat1().toArray().length;
        size5= model.OUT1().toArray().length;
        size6= model.OUT2().toArray().length;

        moves = model.getNumbermove();
        statA = new ArrayList<>();
        statB = new ArrayList<>();
        statA = model.getStat1();
        statB = model.getStat2();




        updateVIew();
        getvalues();
    }


    private void updateVIew(){

        series6 = new XYChart.Series<>();
        for (int i = 0; i < size1; i++) {
        val1.add(model.getTime1()[i]);}
        for (int i = 0; i < size2; i++) {
            val2.add(model.getTime2()[i]);
        }

        for(int i = 0; i < size1; i++){
            //series1.getData().add(new XYChart.Data<>(values1[i], player));
            series1.getData().add(new XYChart.Data<>(val1.get(i), player));


        }

        for(int i = 0; i < size3; i++){
           // series3.getData().add(new XYChart.Data<>(model.getNumbermove().get(i), player));
            series3.getData().add(new XYChart.Data<>(model.getStat1().get(i), player));
            series4.getData().add(new XYChart.Data<>(model.getStat2().get(i), player2));



        }
            for(int i = 0; i < size5; i++){
                series5.getData().add(new XYChart.Data<>(model.OUT1().get(i), player));

            }
        for(int i = 0; i < size6; i++){
            series6.getData().add(new XYChart.Data<>(model.OUT2().get(i), player));

        }



        for(int i = 0; i < size2; i++){
            //series2.getData().add(new XYChart.Data<>(values2[i] , player2));
            series2.getData().add(new XYChart.Data<>(val2.get(i) , player2));



        }
        this.view.getLineChart().getData().clear(); // Clear old data
        this.view.getLineChart().getData().addAll(series1, series2 , series3 , series4 , series5 , series6);


    }




    public void getvalues(){

        for(int i = 0; i < size1; i++){
            values1[i] = model.getTime1()[i];
            val1.add(model.getTime1()[i]);
        }
        for(int i = 0; i < size2; i++){
            values2[i] = model.getTime2()[i];
            val2.add(model.getTime2()[i]);
        }
    }




    public double[] getValues2() {
        return values2;
    }

    public int getSize1() {
        return size1;
    }

    public int getSize2() {
        return size2;
    }

    public double[] getValues1() {
        return values1;
    }

    public double[] getxAxis() {
        return xAxis;
    }

    public List<Integer> stat1() {
        return moves;
    }





}
