package be.kdg.integration2.mvpglobal.view.statscreen;

import be.kdg.integration2.mvpglobal.view.base.BaseView;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatScreenView extends BaseView {

     protected LineChart<Number, String> lineChart;;
     private static final Double[] MAXIMA = {5.7, 6.6, 10.4, 14.2, 18.1, 20.6, 23.0, 22.6, 19.0, 14.7, 9.5, 6.1};
     private static final Double[] MINIMA = {0.7, 0.7, 3.1, 5.3, 9.2, 11.9, 14.0, 13.6, 10.9, 7.8, 4.1, 1.6};
     public Rectangle rectangle;
     public Rectangle rectangle2;
     public Rectangle rectangle3;
     public Rectangle rectangle4;
     //private static  Double[] serie1 = new Double[MAXIMA.length];
    // private static  Double[] serie2 = new Double[MAXIMA.length];
     protected  List<Double> serieA;
    protected  List<Double> serieB;
    protected  List<Double> serieC;

    protected double QA1;
     protected double QA2;
     protected double QA3;
     protected double QB1;
     protected double QB2;
     protected double QB3;


      XYChart.Series<Number, String> series1;
      XYChart.Series<Number, String> series3;
     XYChart.Series<Number, String> series2;
     XYChart.Series<Number, String> series4;


    public StatScreenView() {
          super();
         System.out.println("Fff");
          getserieA();
     }



     protected void layoutNodes() {
          StackPane stackPane = new StackPane();

          stackPane.getChildren().add(lineChart); // Add the chart first

          rectangle = new Rectangle();
          rectangle.setStroke(Color.MEDIUMVIOLETRED);
          rectangle.setFill(Color.TRANSPARENT);
       //   rectangle.setWidth(100);
          rectangle.setHeight(50);
        //  rectangle.setX(MAXIMA[8]);
          rectangle2 = new Rectangle();
          rectangle2.setStroke(Color.MEDIUMVIOLETRED);
          rectangle2.setFill(Color.TRANSPARENT);
        //  rectangle2.setWidth(100);
          rectangle2.setHeight(50);
         // rectangle2.setX(MAXIMA[8]);
          rectangle3 = new Rectangle();
          rectangle3.setStroke(Color.MEDIUMVIOLETRED);
          rectangle3.setFill(Color.TRANSPARENT);
          rectangle3.setWidth( 100);
          rectangle3.setHeight(50);
          rectangle4 = new Rectangle();
          rectangle4.setStroke(Color.MEDIUMVIOLETRED);
          rectangle4.setFill(Color.TRANSPARENT);
          rectangle4.setWidth(100);
          rectangle4.setHeight(50);

          // Position the rectangle in the center
          StackPane.setAlignment(rectangle, Pos.CENTER);
          StackPane.setAlignment(rectangle2, Pos.CENTER);
          StackPane.setAlignment(rectangle3, Pos.CENTER);
          StackPane.setAlignment(rectangle4, Pos.CENTER);
          rectangle.setTranslateX(QA1+64); // Spostamento orizzontale
         // rectangle.setX(MAXIMA[1]);
          rectangle.setTranslateY(MINIMA[1]+60);
          // Spostamento verticale
          rectangle2.setTranslateX(QA2+204); // Spostamento orizzontale
          rectangle2.setTranslateY(MINIMA[1]+60);

          rectangle3.setTranslateX(MAXIMA[1]-170); // Spostamento orizzontale
          rectangle3.setTranslateY(MINIMA[1]-140);
          rectangle4.setTranslateX(MAXIMA[1]-100); // Spostamento orizzontal
          rectangle4.setTranslateY(MINIMA[1]-140);



          stackPane.getChildren().addAll(rectangle , rectangle2 , rectangle3 , rectangle4); // Add the rectangle on top

          setCenter(stackPane); // Set the StackPane as the center
     }

     protected void initialiseNodes() {
          series2 = new XYChart.Series<>();
          series3 = new XYChart.Series<>();
          series1 = new XYChart.Series<>();
          series4 = new XYChart.Series<>();
          lineChart = new LineChart<>(new NumberAxis(), new CategoryAxis());
          serieA = new ArrayList<Double>();
          serieB = new ArrayList<>();
          serieC = new ArrayList<>();

          series1.setName("player");
          series2.setName("ai");
          series3.setName("b");

          /*
          for (int i = 0; i < 10; i++) {
               series1.getData().add(new XYChart.Data<>(MINIMA[i], "bibi"));
               series2.getData().add(new XYChart.Data<>(MAXIMA[i], "ai"));
          }

          lineChart.getData().addAll(series1, series2);
          lineChart.setStyle("-fx-font-size: 20px;");

           */
     }

     public LineChart<Number, String> getLineChart() {
          return lineChart;
     }

     /*

     public void getval(){
          double sumA =0 ;
          double sumB =0 ;
          for (int i = 0; i < series1.getData().size(); i++) {
               // Extracting the XValue (numeric value) from each data point in series1
               // serie1[i] = (Double) series1.getData().get(i).getXValue();
                serieA.add((Double) series1.getData().get(i).getXValue());

                sumA = sumA + (Double) series1.getData().get(i).getXValue();

          }
          // calc avg
          meanA = sumA/series1.getData().size();
          //sort values in order to calculate the quartiles and stat
          Collections.sort(serieA);
          //find quartiles
             p1 = ((serieA.size() )+1 );
          // quartile1A = serieA.get(p1);
        //  quartile3A = serieA.get(((series1.getData().size() +1 )*3)/4);
        //  LoutlierA = quartile1A - (1.5 * (quartile3A - quartile1A));
          if (series1.getData().size() %2 ==0){

          }
        //  UoutlierA = quartile1A + (1.5 * (quartile3A - quartile1A));


          for (int i = 0; i < series2.getData().size(); i++) {
               // Extracting the XValue (numeric value) from each data point in series2
              // serie2[i] = (Double) series2.getData().get(i).getXValue();
               // Casting to Double as needed
               serieB.add((Double) series2.getData().get(i).getXValue());// Casting to Double as needed
               sumB = sumB + (Double) series2.getData().get(i).getXValue();


          }
          meanB = sumB/series2.getData().size();
          //sort values in order to calculate the quartiles and stat
          Collections.sort(serieB);
          //find quartiles
        //  quartile1B = serieB.get((series2.getData().size() +1 )/4);
       //   quartile3B = serieB.get(((series2.getData().size() +1 )*3)/4);
        //  LoutlierB = quartile1B - (1.5 * (quartile3B - quartile1B));
        //  UoutlierB = quartile1B + (1.5 * (quartile3B - quartile1B));

          }

      */


          public  List<Double> getserieA() {
               for (int i = 0; i < series1.getData().size(); i++) {
                    // Extracting the XValue (numeric value) from each data point in series1
                    // serie1[i] = (Double) series1.getData().get(i).getXValue();
                    serieA.add((Double) series1.getData().get(i).getXValue());

                    //sumA = sumA + (Double) series1.getData().get(i).getXValue();

               }
               //sort values in order to calculate the quartiles and stat
               Collections.sort(serieA);
               return serieA;
          }

          public  List<Double> getserieB() {
               for (int i = 0; i < series2.getData().size(); i++) {
                    // Extracting the XValue (numeric value) from each data point in series2
                    // serie2[i] = (Double) series2.getData().get(i).getXValue();
                    // Casting to Double as needed
                    serieB.add((Double) series2.getData().get(i).getXValue());// Casting to Double as needed
                   // sumB = sumB + (Double) series2.getData().get(i).getXValue();


               }
               //sort values in order to calculate the quartiles and stat
               Collections.sort(serieB);
               return serieB;
           }

          public  int statA (){
           List<Double> serieA = getserieA();
               /*
               quartile1A = serieA.get(p1+1);
               p1 = p1*3;
                quartile3A = serieA.get(p1+1);
                LoutlierA = quartile1A - (1.5 * (quartile3A - quartile1A));
               if (series1.getData().size() %2 ==0){

               }
               //  UoutlierA = quartile1A + (1.5 * (quartile3A - quartile1A));

                */


          return serieA.size();
          }

          public void getserieC(List<Double> serieA) {
               serieC = serieA;


          }

          public List<Double> getserie() {
               return serieC;
          }

          public void draw(List<Double> stat){
               QA1 = stat.get(0);
               QA2 = stat.get(1);
               QA3 = stat.get(2);
               rectangle.setWidth((QA2-QA1)*40);
               rectangle2.setWidth((QA3-QA2)*39.8);

          }
























}