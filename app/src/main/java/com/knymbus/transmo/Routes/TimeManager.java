package com.knymbus.transmo.Routes;

import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.knymbus.transmo.Helper.Helper;
import com.knymbus.transmo.Helper.SystemInterface;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class TimeManager {

    public Runnable ticker;

    public Integer percentage(int numerator, int denominator){
        double total = 0.0;

        if(denominator == 0) return 0;

        total = ((float) numerator / denominator) * 100;

        return (int)Math.round(total);
    }

    public double percentage(int numerator, int denominator, int decimalsplaces){
        double total = 0.0;
        double finalValue = 0;
        if(denominator == 0){
            return 0;
        }

        total = ((float) numerator / denominator) * 100;

        BigDecimal bigDecimal = new BigDecimal(total);
        BigDecimal roundOFF = bigDecimal.setScale(decimalsplaces, BigDecimal.ROUND_HALF_EVEN);

        if(decimalsplaces == 2){
            DecimalFormat df = new DecimalFormat("0.00");
            String formatx = df.format(roundOFF);
            try {
                return (double)df.parse(formatx);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

//        TODO: create a default value
        return 0;

    }

    /*
     * accept doubles as inputs and return percentage as double
     *
     * @params:
     * @return double e.g. 45.56
     *
     */
    public double percentage(double numerator, double denominator, int decimalsplaces){
        double total = 0.0;
        double finalValue = 0;
        if(denominator == 0){
            return 0;
        }

        total = ((float) numerator / denominator) * 100;

        BigDecimal bigDecimal = new BigDecimal(total);
        BigDecimal roundOFF = bigDecimal.setScale(decimalsplaces, BigDecimal.ROUND_HALF_EVEN);

        if(decimalsplaces == 2){
            DecimalFormat df = new DecimalFormat("0.00");
            String formatx = df.format(roundOFF);
            return Double.valueOf(formatx);
        }

        if(decimalsplaces == 1){
            DecimalFormat df = new DecimalFormat("0.0");
            String formatx = df.format(roundOFF);
            return Double.valueOf(formatx);
        }

        return 0;

    }

    /*
     *
     * This is function that will take two dates and calculate the difference in time
     */
    public String timeDiff(Timestamp timestamp1, Timestamp timestamp2){
        Map timeDiff = getTimeDiff(timestamp1,timestamp2);
        int hours = (int)timeDiff.get(time.hours);
        int minutes = (int)timeDiff.get(time.minutes);


        if(hours == 0 && minutes > 1){
            return Helper.stringBuilder("%dm",minutes);
        }

        if(hours == 0 && minutes == 1){
            return Helper.stringBuilder("%dm",minutes);
        }

//        TODO: discuss what happen if hour == 0 and minutes ==0

        if (hours > 1 && minutes > 1){
            return Helper.stringBuilder("%dh %dm",hours,minutes);
        }

        if(hours > 1 && minutes == 0){
            return Helper.stringBuilder("%dh",hours);
        }

        if(hours == 1 && minutes == 0) {
            return Helper.stringBuilder("%dh", hours);
        }

        return Helper.stringBuilder("%dh %dm",hours,minutes);
    }

    /**
     *
     * @param postTime
     * @return
     */
    public void timeLaspe(final Timestamp postTime, final TextView tvtime, final TextView tvUnit ){

        final Handler handler = new Handler();
        ticker = new Runnable() {
            @Override
            public void run() {

                Date timeNow = new Date();
                Map diffInTime = getTimeDiff(timeNow,postTime);
                long now = SystemClock.uptimeMillis();
                long next = now + (1000 - now % 1000);
                //find the time difference between now postTime and update the textview
                int hr = Integer.parseInt(diffInTime.get(time.hours).toString());
                int mins = Integer.parseInt(diffInTime.get(time.minutes).toString());
                int sec = Integer.parseInt(diffInTime.get(time.seconds).toString());
                int day = Integer.parseInt(diffInTime.get(time.day).toString());
                int flag = Integer.parseInt(diffInTime.get(time.flag).toString());
                String s, u;


                //If the day is 1 to 7 then
                if(day > 0 && day < 8){
                    s = Helper.stringBuilder("%d",day);
                    u = Helper.stringBuilder("Day");
                    tvtime.setText(s);
                    tvUnit.setText(u);
                }

                //If day greater than 7 days then we show the date
                if(day > 7){
                    //To display the date and year we need to know if its the current year then show e.g. Dec 19
                    //other wise show Dec 19, 2018
                    tvtime.setText(formatDate(postTime));
                    tvUnit.setText("week");
                }

                //

                if(day < 1 && hr > 1 && hr < 24 ){
                    tvtime.setText(formatTime(hr,mins,sec));


                    if(flag == 1){
                        tvUnit.setText("hrs ago");
                    }else{
                        tvUnit.setText("hrs");
                    }
                }

                if(day == 0 && mins > 0 && mins < 59 ){
                    tvtime.setText(Helper.stringBuilder("%d",mins));

                    if(flag == 1){
                        tvUnit.setText("min ago");
                    }else{
                        tvUnit.setText("min");
                    }

                }

                if(day == 0 && mins == 0 && sec > 0 && sec < 60){
                    tvtime.setText(Helper.stringBuilder(""));
                    tvUnit.setText("now");
                }



                handler.postAtTime(ticker, next);

            }
        };
        ticker.run();

    }


    /**
     *
     * @param timestamp Firebase timestamp object
     * @return String  e.g. Dec 12, 2018 || Dec 12
     */
    public static String formatDate(Timestamp timestamp){
        //Variables
        int year = Integer.parseInt(Helper.DateFormatter(SystemInterface.DateTimeFormat.year, timestamp.toDate()));
        int currentYear = Integer.parseInt(Helper.DateFormatter(SystemInterface.DateTimeFormat.year,new Date()));

        if(year == currentYear){
            return (Helper.DateFormatter(SystemInterface.DateTimeFormat.shortMonthDate,timestamp.toDate()));
        }else{
            return (Helper.DateFormatter(SystemInterface.DateTimeFormat.dateMonthYear,timestamp.toDate()));
        }

    }

    private static String formatTime(int hr, int mins, int sec){

        if(hr > 0 && hr < 24){
            return  Helper.stringBuilder("%d",hr);
        }

        //if minutes greater than 1 and less than 59 then show minutes e.g 4 Minutes aga
        if(hr < 1 && mins > 0 && mins < 60){
            return  Helper.stringBuilder("%d",mins);
        }

        if(sec > 0 && sec < 59){
            return "Now";
        }
        return "";
    }

    /**
     *
     * @param timestamp1
     * @param timestamp2
     * @return
     */
    public static Double timeDiffToDouble(Timestamp timestamp1, Timestamp timestamp2){
        Map result = getTimeDiff(timestamp1,timestamp2);
        double hrs = Double.valueOf(result.get(time.hours).toString());

//        convert minutes to decimal representation that is 30/60 = 0.50
        double mins = + Double.valueOf(result.get(time.minutes).toString()) / 60;

        /* return the duration as a decimal representing time e.g. 4hrs + 0.50 decimal minutes = 4.5 */
        return hrs + mins;

    }

    /**
     *
     * @param t1
     * @param t2
     * @return
     */
    private static Map<String, Integer> getTimeDiff(Timestamp t1, Timestamp t2){
        Map<String, Integer>result = new HashMap<>();

        long elapse;
        long time1 = t1.getSeconds();
        long time2 = t2.getSeconds();

        if(time1 > time2){
            elapse = time1 - time2;
            result.put(time.flag, 1);
        }else{
            elapse = time2 -time1;
        }

        int hours = (int)elapse / 3600;
        int minutes = ((int)elapse % 3600) / 60;
        int seconds = ((int)elapse % 3600) % 60;

        result.put(time.hours, hours);
        result.put(time.minutes, minutes);
        result.put(time.seconds, seconds);

        return result;

    }

    /**
     *
     * @param t1
     * @param t2
     * @return
     */
    private Map<String, Integer> getTimeDiff(Date t1, Timestamp t2){
        Map<String, Integer>result = new HashMap<>();

        Timestamp t3 = new Timestamp(t1);
        long elapse;
        long time1 = t3.getSeconds();
        long time2 = t2.getSeconds();

        result.put(time.flag, 0);

        if(time1 > time2){
            elapse = time1 - time2;
            result.put(time.flag, 1);
        }else{
            elapse = time2 -time1;
        }

        //seconds in hours = 60mins * 60secs = 3600 secs per hour
        int day = 0;
        int hours = ((int)elapse > 0 ? (int)elapse / 3600  : 0);
        int minutes = ((int)elapse / 60);
        int seconds = (int)elapse;

        if(hours >= 24){
            day = hours / 24;
        }

        result.put(time.hours, hours);
        result.put(time.minutes, minutes);
        result.put(time.seconds, seconds);
        result.put(time.day, day);

        return result;

    }

    /**
     *
     * @param actualWorked
     * @return
     */
    public static String DurationToTime(double actualWorked) {
//        value from param e.g. 10.77
//        0.77 is the % of the minutes within an hour
//        60 minutes X 0.77 = 46 minutes

//        first thing is to separate the decimal and the whole number of the param
        int hrs = (int)Math.floor(actualWorked);
        int minsInPercentage = (int)((actualWorked * 100) - (hrs * 100));

        int minutes = (int)Math.round(minsInPercentage * 0.6);

//        this function will return hours is any minutes if any therefore we must now check and return appropriately

//      if hrs and minute greater than 0 then display both
        if(hrs > 0 && minutes > 0){
            return Helper.stringBuilder("%dh %dm",hrs,minutes);
        }

//      if hrs greater than 0 and minute equal 0 then display hrs only
        if(hrs > 0 && minutes == 0){
            return Helper.stringBuilder("%dh",hrs);
        }

//      if hrs equal 0 and minute greater than 0
        if(hrs == 0 && minutes > 0){
            return Helper.stringBuilder("%dm",minutes);
        }

//      TODO: create some default return out put
        return "";
    }





    public interface time{
        String hours = "hours";
        String minutes = "minutes";
        String seconds = "seconds";
        String day = "day";
        String flag = "flag";
    }

}
