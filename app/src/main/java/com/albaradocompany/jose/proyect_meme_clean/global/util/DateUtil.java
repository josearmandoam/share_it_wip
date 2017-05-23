package com.albaradocompany.jose.proyect_meme_clean.global.util;

import java.util.Calendar;

/**
 * Created by jose on 15/05/2017.
 */

public class DateUtil {
    public static String timeAgo(String date, String time) {
        if (date.equals(getCurrentDateFormated())) {
            long timeParsed = Long.parseLong(time);
            long currentTimeParse = Long.parseLong(getCurrentTime());

            long diference = currentTimeParse - timeParsed;

        }
        return "wip";
    }

    public static String getCurrentDateFormated() {
        Calendar calendar = Calendar.getInstance();
        String date = "" + calendar.get(calendar.YEAR) + "-" + (calendar.get(calendar.MONTH) + 1) + "-" + calendar.get(calendar.DAY_OF_MONTH);
        return date;
    }

    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        String date = "" + calendar.get(calendar.YEAR) + (calendar.get(calendar.MONTH) + 1) + calendar.get(calendar.DAY_OF_MONTH);
        return date;
    }

    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        String hr = Integer.toString(calendar.get(calendar.HOUR_OF_DAY));
        String min = Integer.toString(calendar.get(calendar.MINUTE));
        String ss = Integer.toString(calendar.get(calendar.SECOND));
        if (Integer.parseInt(hr) < 10)
            hr = "0" + hr;
        if (Integer.parseInt(min) < 10)
            min = "0" + min;
        if (Integer.parseInt(ss) < 10)
            ss = "0" + ss;

        String time = hr + min + ss;
        return time;
    }

    public static String getCurrentTimeFormated() {
        Calendar calendar = Calendar.getInstance();
        String hr = Integer.toString(calendar.get(calendar.HOUR_OF_DAY));
        String min = Integer.toString(calendar.get(calendar.MINUTE));
        String ss = Integer.toString(calendar.get(calendar.SECOND));
        if (Integer.parseInt(hr) < 10)
            hr = "0" + hr;
        if (Integer.parseInt(min) < 10)
            min = "0" + min;
        if (Integer.parseInt(ss) < 10)
            ss = "0" + ss;

        String time = hr + ":" + min + ":" + ss;
        return time;
    }

    public static String getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        String datetime = "" + calendar.get(calendar.DAY_OF_MONTH) + calendar.get(calendar.MONTH)
                + calendar.get(calendar.YEAR) + calendar.get(calendar.HOUR_OF_DAY) + calendar.get(calendar.SECOND) + calendar.get(Calendar.MILLISECOND);
        return datetime;
    }
}
