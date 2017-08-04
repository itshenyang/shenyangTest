package com.keruis.cilent.utils;


import org.junit.Test;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2017/4/7.
 */
public class TimeUtils {//yyyy-MM-dd HH:mm:ss
    private DateFormat dateYear = new SimpleDateFormat("yyyy");
    private DateFormat dateMonth = new SimpleDateFormat("MM");
    private DateFormat dateDay = new SimpleDateFormat("dd");
    private DateFormat dateHH = new SimpleDateFormat("HH");
    private DateFormat dateMM = new SimpleDateFormat("mm");
    private DateFormat datess = new SimpleDateFormat("ss");

    public Timestamp getTimeNow() {
        return new Timestamp(System.currentTimeMillis());
    }

    public String getYYMMddhhmmssByTime(Timestamp timestamp) {
        return dateYear.format(timestamp) + "-" + dateMonth.format(timestamp) + "-" + dateDay.format(timestamp) + " " + dateHH.format(timestamp) + ":" + dateMM.format(timestamp) + ":" + datess.format(timestamp);
    }

    public String getYYMMddByTime(Timestamp timestamp) {
        return dateYear.format(timestamp) + "-" + dateMonth.format(timestamp) + "-" + dateDay.format(timestamp);
    }

    public String getYYMMByTime(Timestamp timestamp) {
        return dateYear.format(timestamp) + "-" + dateMonth.format(timestamp);
    }

    public Timestamp get1CurrentMonth() {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public Timestamp get1LastMonth() {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public List<String> getSameMonthTimeRangeAllYYmmdd(Timestamp startTime, Timestamp overTime) {
        List<String> result = new ArrayList<String>();
        Calendar calendarstar = Calendar.getInstance();
        calendarstar.setTime(startTime);
        Calendar calendarvoer = Calendar.getInstance();
        calendarvoer.setTime(overTime);
        for (int i = Integer.parseInt(dateDay.format(startTime)); i <= calendarstar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {

            result.add(dateYear.format(calendarstar.getTime()) + "-" + dateMonth.format(calendarstar.getTime()) + "-" + dateDay.format(calendarstar.getTime()));
            calendarstar.add(Calendar.DATE, 1);
        }
        return result;
    }

    public Timestamp getTimeLastTime(Timestamp timestamp) {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.setTime(timestamp);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);//
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public List<String> getLastAndNext5Min(Timestamp timestamp) {
        List<String> list = new ArrayList<>();
        for (Long i = timestamp.getTime() - 4 * 60 * 1000; i <= timestamp.getTime() + 5 * 60 * 1000; i = i + 1000) {
            list.add(getYYMMddhhmmssByTime(new Timestamp(i)));
        }
        return list;
    }


    @Test
    public void test() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        L.w(RedisUtils.getDeviceDataTableName(1163L, getYYMMByTime(timestamp)) + "");
        L.w(getLastAndNext5Min(getTimeNow()).toString());
    }


}
