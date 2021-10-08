package org.wayne.common.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: lwq
 */
public class DateUtilQ {
    final static SimpleDateFormat YYYY_MM_DD_FORMAT;
    final static SimpleDateFormat MM_DD_FORMAT;
    final static SimpleDateFormat HH_MM_SS_SDF;

    static {
        YYYY_MM_DD_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        HH_MM_SS_SDF = new SimpleDateFormat("HH:mm:ss");
        //严格日期检查
        YYYY_MM_DD_FORMAT.setLenient(false);
        MM_DD_FORMAT = new SimpleDateFormat("MM-dd");
        MM_DD_FORMAT.setLenient(false);
    }
    /**
     * 获取下一个工作日
     * @param dataDay     当天
     * @param freeDayList 休息日集合
     */
    public static String getNextWorkDay(String dataDay, List<String> freeDayList) throws ParseException {
        dataDay = addOrSubDay(dataDay, 1);
        //休息日
        while (freeDayList.contains(dataDay)) {
            dataDay = addOrSubDay(dataDay, 1);
        }
        return dataDay;
    }

    /**
     * 两个日期间隔天数
     */
    public static int getBetweenDayCounts(String minDate, String bigDate) throws ParseException {
        final Date p1 = YYYY_MM_DD_FORMAT.parse(bigDate);
        final Date p2 = YYYY_MM_DD_FORMAT.parse(minDate);
        return (int) (Math.abs(p1.getTime() - p2.getTime()) / (60 * 60 * 24 * 1000));
    }

    /**
     * 日期加减
     *
     */
    public static String addOrSubDay(String date, int count) throws ParseException {
        final Date parse = YYYY_MM_DD_FORMAT.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parse);
        calendar.add(Calendar.DAY_OF_MONTH, count);
        return YYYY_MM_DD_FORMAT.format(calendar.getTime());
    }

    public static Date addOrSubDay(Date date, int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, count);
        return calendar.getTime();
    }

    /**
     * 获取2个日期之间的日期数组
     *
     */
    public static List<Date> getBetweenDayArray(Date start, Date end) {
        List<Date> dates = new ArrayList<>();
        dates.add(start);
        if (start.compareTo(end) == 0) {
            return dates;
        }
        while (true) {
            start = addOrSubDay(start, 1);
            if (start.after(end) || start.compareTo(end) == 0) {
                break;
            }
            dates.add(start);
        }
        dates.add(end);
        return dates;
    }

    public static List<String> getBetweenDayArray(String start, String end) throws ParseException {
        List<String> dates = new ArrayList<>();
        dates.add(start);
        if (dates.contains(end)) {
            return dates;
        }
        while (true) {
            start = addOrSubDay(start, 1);
            if (YYYY_MM_DD_FORMAT.parse(start).after(YYYY_MM_DD_FORMAT.parse(end)) || start.equals(end)) {
                break;
            }
            dates.add(start);
        }
        dates.add(end);
        return dates;
    }

    public static String getCurrentDate(){
        return YYYY_MM_DD_FORMAT.format(new Date());
    }
    public static String getCurrentTime(){
        return HH_MM_SS_SDF.format(new Date());
    }


}
