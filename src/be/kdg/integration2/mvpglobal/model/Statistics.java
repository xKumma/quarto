package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.dbconnection.DBManager;

import java.sql.SQLException;
import java.util.ArrayList;import java.util.Collections;
import java.util.List;

public class Statistics implements BaseModel {
    public static int i =1;
    public static int j =1;
    protected  double meanA;
    protected  double meanB;
    protected double quartile1A;
    protected double quartile2A;
    protected double quartile3A;
    protected double quartile1B;
    protected double quartile2B;
    protected double quartile3B;
    protected double LoutlierA;
    protected double LoutlierB;
    protected double UoutlierA;
    protected double UoutlierB;
    protected double RIQA;
    protected double RIQB;
    protected List<Double> stat1 = new ArrayList<>();
    protected String firstname;
    protected double score;
    protected  double time;

    protected static double[] time1 ;
    protected static double[] time2 ;
    protected  List <Double> timeList1 = new ArrayList<>();
    protected List <Double> timeList2 = new ArrayList<>();



    public Statistics()  {
    }

    public  void setValues() throws SQLException {
        if(!DBManager.getInstance().isAI(j)) {
            timeList1.add(DBManager.getInstance().getTimeMove2(i, j));
        } else {timeList2.add(DBManager.getInstance().getTimeMove1(i , j ));}
        j++;

    }


    public void launch() throws SQLException {
            for (int i = DBManager.getInstance().getMoveID1(1); i <= DBManager.getInstance().getMoveID2(1 ); i ++){
                try {
                    setValues();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            i++;

          //  System.out.println(DBManager.getInstance().getTimeMove1(1, j));
          //  System.out.println(DBManager.getInstance().getTimeMove2(1,j));
        Collections.sort(timeList1);
          System.out.println(timeList1);
          System.out.println(timeList2);

                   time1 = new double[timeList1.size()];
                   time2 = new double[timeList2.size()];

          for(int i=0 ; i<timeList1.size(); i++){
              time1 [i] = timeList1.get(i);
              System.out.println(time1[i]);

          }

          for(int i=0 ; i<timeList2.size(); i++){
              time2 [i] = timeList2.get(i);
              System.out.println(time2[i]);
          }
        statA();
        System.out.println(stat1);
        System.out.println((int)((timeList1.size()+1)*0.25));


    }

    public void statA(){
        double p1;
         int j=1;
        Collections.sort(timeList1);
       for(int i=0 ; i<timeList1.size(); i++) {
           if (timeList1.size() % 2 == 0) {

               p1= ((timeList1.size())*0.25);
               int result = (int) Math.ceil(p1);


               if (j == result) {
                   quartile1A = timeList1.get(result - 1);
                   stat1.add(quartile1A);
               }


               if (j == result * 2) {
                   double a =timeList1.get((timeList1.size()/2-1));
                   double b = timeList1.get((timeList1.size()/2));
                   quartile2A = (a+b)*0.5;
                   stat1.add(quartile2A);
               }

               if (j == result * 3) {
                   quartile3A = timeList1.get(result * 3 - 2);
                   stat1.add(quartile3A);
               }
               j++;
           }else{
               p1= ((timeList1.size()+1)*0.25);
               int result = (int) Math.ceil(p1);


               if (j == result) {
                   quartile1A = timeList1.get(result - 1);
                   stat1.add(quartile1A);
               }


               if (j == result * 2) {
                   quartile2A = timeList1.get(result * 2 - 1);
                   stat1.add(quartile2A);

                   }


               if (j == result * 3) {
                   quartile3A = timeList1.get(result * 3 - 1);
                   stat1.add(quartile3A);
               }
               j++;

           }
       }
    }

    public double[] getTime1() {
        return time1;
    }
    public double[] getTime2() {
        return time2;
    }

    public double getQuartile1A() {
        return quartile1A;
    }

    public double getQuartile2A() {
        return quartile2A;
    }

    public double getQuartile3A() {
        return quartile3A;
    }

    public List<Double> stat1() {
        return stat1;
    }

    public String getFirstname() {
        firstname = "bibi";
        return firstname;
    }

    public double getScore() {
        score = 100;
        return score;
    }

    public double getTime() {
        time = 10;
        return time;
    }
}
