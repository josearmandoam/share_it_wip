package com.albaradocompany.jose.proyect_meme_clean.global.util;

import java.util.Calendar;

/**
 * Created by jose on 15/05/2017.
 */

public class DateUtil {
    public static String timeAgo(String date, String time){
        if (date.equals(getCurrentDate())){
            long timeParsed = Long.parseLong(time);
            long currentTimeParse = Long.parseLong(getCurrentTime());

            long diference = currentTimeParse - timeParsed;

        }
        return "wip";
    }
    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        String date = "" + calendar.get(calendar.DAY_OF_MONTH) + calendar.get(calendar.MONTH)
                + calendar.get(calendar.YEAR);
        return date;
    }
    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        String time = ""+calendar.get(calendar.HOUR_OF_DAY) +calendar.get(calendar.MINUTE)+ calendar.get(calendar.SECOND);
        return time;
    }
    public static String getCurrentDateTime(){
        Calendar calendar = Calendar.getInstance();
        String datetime = "" + calendar.get(calendar.DAY_OF_MONTH) + calendar.get(calendar.MONTH)
                + calendar.get(calendar.YEAR) + calendar.get(calendar.HOUR_OF_DAY) + calendar.get(calendar.SECOND) + calendar.get(Calendar.MILLISECOND);
        return datetime;
    }
}
