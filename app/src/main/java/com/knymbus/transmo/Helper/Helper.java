package com.knymbus.transmo.Helper;

import com.google.firebase.Timestamp;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.Formatter;

public abstract class Helper {

    public static String DateFormatter(String format, java.util.Date myDate){
        DateFormat df = new SimpleDateFormat(format, Locale.US);
        Date d = myDate;
        return df.format(myDate.getTime());
    }

    public static String DateFormatter(String format, Timestamp timestamp){
        DateFormat df = new SimpleDateFormat(format,Locale.US);
        return df.format(timestamp);
    }

    public static String createStartTime(){
        return String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
    }


    public static String stringBuilder(String s, Object ... args ){
        StringBuilder stringBuilder = new StringBuilder();
        Formatter fmt = new Formatter(stringBuilder);
        return fmt.format(s,args).toString();
    }

    public static String formatToMoney(double num){
       return NumberFormat.getCurrencyInstance(new Locale("en", "US"))
                .format(num);

    }

    public static String generateCardBalance(){
        return "0";
    }


    public static String splitString(String s){
        return s.split("-")[0].trim();
    }

    public static String maskedCardNumber(String s){
        String[] strings = s.split("-");
        String r = "";
        for (int i=0;i<3;i++){
            r += " " +strings[i].replaceAll("[0-9]","X");
        }
        r += " " + strings[3];
        return r;
    }
}
