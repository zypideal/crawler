package com.zyp.crawler.util;

import java.util.Calendar;
import java.util.TimeZone;

public class TimeUtil {

    private static final TimeZone UTC = TimeZone.getTimeZone("UTC");
    private static final TimeZone SHANGHAI = TimeZone.getTimeZone("Asia/Shanghai");


    public static long getCurrentTimeInMillis(){
        return Calendar.getInstance(SHANGHAI).getTimeInMillis();
    }


    public static Calendar getTodayStartTime(){
        Calendar calendar = Calendar.getInstance(SHANGHAI);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }


    public static Calendar getTodayEndTime(){
        Calendar calendar = Calendar.getInstance(SHANGHAI);

        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }


    public static long getTodayStartTimeInMillis(){
        Calendar calendar = getTodayStartTime();
        return calendar.getTimeInMillis();
    }


    public static long getTodaEndTimeInMillis(){
        Calendar calendar = getTodayEndTime();
        return calendar.getTimeInMillis();
    }

}
