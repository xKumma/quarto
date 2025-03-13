package be.kdg.integration2.mvpglobal.view.statscreen;

import be.kdg.integration2.mvpglobal.model.Statistics;
import be.kdg.integration2.mvpglobal.view.base.BasePresenter;
import javafx.scene.chart.XYChart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatScreenPresenter extends BasePresenter<StatScreenView, Statistics> {
    /*
    ObservableList<LineChart.Data> lineChartData =
            FXCollections.observableArrayList();

     */

    protected double[] values1;
    int size1;
    int size3;
    protected double[] values2;
    int size2;
    protected double[] xAxis;
    protected String player = "bibi";
    protected String player2 = "ai";
    protected List<Double> val1;
    protected List<Double> val2;
    protected List<Double> stat;



    public StatScreenPresenter(StatScreenView view, Statistics model) {
        super(view, model);

        if (this.model == null) {
            System.out.println("model is null");
            return;
        }


        //arrayx();
        updateVIew();
    }

    private void init() {

        try {
            model.launch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        size1 = model.getTime1().length;
        values1 = new double[size1];
        size2 = model.getTime2().length;
        values2 = new double[size2];
        xAxis = new double[size2];
        val1 = new ArrayList<>();
        val2 = new ArrayList<>();
        size3= model.stat1().toArray().length;
        stat = model.stat1();

        getvalues();
    }


    private void updateVIew(){
        XYChart.Series<Number, String> series1 = new XYChart.Series<>();
        XYChart.Series<Number, String> series2 = new XYChart.Series<>();
        XYChart.Series<Number, String> series3 = new XYChart.Series<>();
        XYChart.Series<Number, String> series4 = new XYChart.Series<>();

        for(int i = 0; i < size1; i++){
            //series1.getData().add(new XYChart.Data<>(values1[i], player));
            series1.getData().add(new XYChart.Data<>(val1.get(i), player));


        }
        for(int i = 0; i < size3; i++){
            //series1.getData().add(new XYChart.Data<>(values1[i], player));
            series3.getData().add(new XYChart.Data<>(model.stat1().get(i), player));


        }

        for(int i = 0; i < size2; i++){
            //series2.getData().add(new XYChart.Data<>(values2[i] , player2));
            series2.getData().add(new XYChart.Data<>(val2.get(i) , player2));



        }
        this.view.getLineChart().getData().clear(); // Clear old data
        this.view.getLineChart().getData().addAll(series1, series2 , series3); // Add both series
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

    /*
    
    public double findmax(){
        double max = 0;
        double max1=0;
        for(int i = 0; i < size1; i++){
            if(values1[i] > values1[i+1]){
                max = values1[i];
            }
        }
        for(int i = 0; i < size2; i++){
            if(values2[i] > values2[i+1]){
                max1 = values2[i];
            }
        }

        if(max > max1){
            return max;
        }else{
            return max1;
        }

    }

    public void arrayx(){
        double max = findmax()*2;
        double unit = max/((statistics.getTime1().length+statistics.getTime1().length)/4);

        for(int i = 0; i < max; i++){
            xAxis[i] = (i)*unit;
        }

    }

     */
    public void stat(){
        view.statA();

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

    public List<Double> stat1() {
        return stat;
    }




}
