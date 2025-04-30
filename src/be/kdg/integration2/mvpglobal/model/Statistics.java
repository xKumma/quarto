package be.kdg.integration2.mvpglobal.model;

import be.kdg.integration2.mvpglobal.utility.dbconnection.DBManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Statistics implements BaseModel {
    public static DBManager dbManager;
    public static int i =1;
    public static int j =1;

    protected double quartile1A;
    protected double quartile2A;
    protected double quartile3A;
    protected double quartile1B;
    protected double quartile2B;
    protected double quartile3B;
    protected double outlier1A;
    protected double IQRA;
    protected double IQRB;
    protected double outlier2A;
    protected double outlier2B;
    protected double outlier1B;


    protected List<Double> stat1 = new ArrayList<>();
    protected List<Double> stat2 = new ArrayList<>();
    protected List<Double> out1 = new ArrayList<>();
    protected List<Double> out2 = new ArrayList<>();

    protected String firstname;
    protected double score;
    protected  double time;


    protected static double[] time1 ;
    protected static double[] time2 ;
    protected  List <Double> timeList1 = new ArrayList<>();
    protected List <Double> timeList2 = new ArrayList<>();
    protected  List <Integer> numbermove = new ArrayList<>();




    public Statistics()  {
    }

    public  void setValues() throws SQLException {
        if(dbManager.isAI(i)==false) {
            System.out.println("sessione"+j + "\n");
            System.out.println("move"+i + "\n");
            System.out.println(dbManager.isAI(i) + "\n" );
            System.out.println("time"+dbManager.getTimeMove2(j, i)+ "\n");
            timeList1.add(dbManager.getTimeMove2(j, i));
            numbermove.add( i);
            i++;
        } else {
            System.out.println("sessione"+j + "\n");
            System.out.println(dbManager.isAI(i) + "\n" );
            System.out.println("initial move"+i + "\n");
            System.out.println("time"+dbManager.getTimeMove1(j, i)+ "\n");
            timeList2.add(dbManager.getTimeMove1(j , i ));
            numbermove.add( i);

            i++;
        }


    }


    public void launch() throws SQLException {
        j= dbManager.getSessionid();
        i=dbManager.getMoveID1(j);
        System.out.println("sessione"+j + "\n");
        System.out.println("initial move"+i + "\n");

        for (int i = dbManager.getMoveID1(j); i <= dbManager.getMoveID2(j ); i ++){
                try {
                    setValues();
                    System.out.println("ciao");
                } catch (SQLException e) {
                }
            }




time();



    }

    public void time(){

        //  System.out.println(dbManager.getTimeMove1(1, j));
        //  System.out.println(dbManager.getTimeMove2(1,j));
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
        statB();
        outA();
        outB();





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

        System.out.println(stat1);
    }


    public void statB(){
        double p1;
        int j=1;
        Collections.sort(timeList2);
        for(int i=0 ; i<timeList2.size(); i++) {
            if (timeList2.size() % 2 == 0) {
                p1= ((timeList2.size())*0.25);
                int result = (int) Math.ceil(p1);
                if (j == result) {
                    quartile1B = timeList2.get(result - 1);
                    stat2.add(quartile1B);
                }
                if (j == result * 2) {
                    double a =timeList2.get((timeList2.size()/2-1));
                    double b = timeList2.get((timeList2.size()/2));
                    quartile2B = (a+b)*0.5;
                    stat2.add(quartile2B);
                }

                if (j == result * 3) {
                    quartile3B = timeList2.get(result * 3 - 2);
                    stat2.add(quartile3B);
                }
                j++;
            }else{
                p1= ((timeList2.size()+1)*0.25);
                int result = (int) Math.ceil(p1);


                if (j == result) {
                    quartile1B = timeList2.get(result - 1);
                    stat2  .add(quartile1B    );
                }
                if (j == result * 2) {
                    quartile2B = timeList2.get(result * 2 - 1);
                    stat2.add(quartile2B);

                }
                if (j == result * 3) {
                    quartile3B = timeList2.get(result * 3 - 1);
                    stat2.add(quartile3B);
                }
                j++;

            }
        }
        System.out.println(stat2);
    }

    public void outA(){
        IQRA = quartile3A-quartile1A;
         outlier1A = quartile1A - 1.5*IQRA;
         outlier2A = quartile3A + 1.5*IQRA;

         for (int i=0 ; i<timeList1.size(); i++){
             if ( timeList1.get(i) <= outlier1A | timeList2.get(i) >= outlier2A){
                 out1.add(timeList1.get(i));
             }
         }

         System.out.println(out1);

    }

    public void outB(){
        IQRB = quartile3B-quartile1B;
        outlier1B = quartile1B - 1.5*IQRB;
        outlier2B = quartile3B + 1.5*IQRB;

        for (int i=0 ; i<timeList2.size(); i++){
            if ( timeList2.get(i) <= outlier1B | timeList2.get(i) >= outlier2B){
                out2.add(timeList2.get(i));
            }
        }
        System.out.println(out2);

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

    public List<Integer> getNumbermove() {
        return numbermove;
    }

    public List<Double> getStat1() {
        return stat1;
    }

    public List<Double> getStat2() {
        return stat2;
    }

    public List<Double> OUT2() {
        return out2;
    }

    public List<Double> OUT1() {
        return out1;
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
