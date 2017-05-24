package com.albaradocompany.jose.proyect_meme_clean.global.util;

import java.util.Calendar;

/**
 * Created by jose on 15/05/2017.
 */

public class DateUtil {
    public static String timeAgo(String date, String time) {
        if (date.equals(getCurrentDateFormated())) {
            String hr = time.substring(0, 2);
            String mm = time.substring(3, 5);
            String ss = time.substring(6, 8);
            String ctime = getCurrentTimeFormated();
            String chr = ctime.substring(0, 2);
            String cmm = ctime.substring(3, 5);
            String css = ctime.substring(6, 8);
            if (Integer.parseInt(chr) - Integer.parseInt(hr) > 0)
                return Integer.parseInt(chr) - Integer.parseInt(hr) + " hr";
            else if (Integer.parseInt(cmm) - Integer.parseInt(mm) > 0)
                return Integer.parseInt(cmm) - Integer.parseInt(mm) + " min";
            else
                return Integer.parseInt(css) - Integer.parseInt(ss) + " seg";
        } else {
            String dd = date.substring(8, 10);
            String mm = date.substring(5, 7);
            String cdate = getCurrentDateFormated();
            String cdd = cdate.substring(8, 10);
            String cmm = cdate.substring(5, 7);
            if (mm.equals(cmm))
                return Integer.parseInt(cdd) - Integer.parseInt(dd) + " d";
            else
                return Integer.parseInt(cmm) - Integer.parseInt(mm) + " m";
        }
    }

    public static String getCurrentDateFormated() {
        Calendar calendar = Calendar.getInstance();
        String yyyy = Integer.toString(calendar.get(calendar.YEAR));
        String mm = Integer.toString(calendar.get(calendar.MONTH) + 1);
        String dd = Integer.toString(calendar.get(calendar.DAY_OF_MONTH));
        if (Integer.parseInt(mm) < 10)
            mm = "0" + mm;
        if (Integer.parseInt(dd) < 10)
            dd = "0" + dd;
        String date = "" + yyyy + "-" + mm + "-" + dd;
        return date;
    }

    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        String yyyy = Integer.toString(calendar.get(calendar.YEAR));
        String mm = Integer.toString(calendar.get(calendar.MONTH) + 1);
        String dd = Integer.toString(calendar.get(calendar.DAY_OF_MONTH));
        if (Integer.parseInt(mm) < 10)
            mm = "0" + mm;
        if (Integer.parseInt(dd) < 10)
            dd = "0" + dd;
        String date = "" + yyyy + mm + dd;
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
