package com.lv.mysdk.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtils {
    /**
     * 通过年份和月份 得到当月的日子
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthDays(int year, int month) {
        month++;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return -1;
        }
    }

    /**
     * 返回当前月份1号位于周几
     *
     * @param year  年份
     * @param month 月份，传入系统获取的，不需要正常的
     * @return 日：1		一：2		二：3		三：4		四：5		五：6		六：7
     */
    public static int getFirstDayWeek(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 根据列明获取周
     *
     * @param column
     * @return
     */
    public static String getWeekName(int column) {
        switch (column) {
            case 0:
                return "周日";
            case 1:
                return "周一";
            case 2:
                return "周二";
            case 3:
                return "周三";
            case 4:
                return "周四";
            case 5:
                return "周五";
            case 6:
                return "周六";
            default:
                return "";
        }
    }

    //把日期转为字符串
    public static String ConverToString(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        return df.format(date);
    }

    //把日期转为字符串
    public static String ConverToString2(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    //带时 分；
    public static String ConverToStringtoss(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return df.format(date);
    }

    //把字符串转为日期
    public static Date ConverToDate(String strDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date StringToDate(String strDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String DateToString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    //字符串转为Timestamp
    public static long StringToTimeStamp(String strDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeLong = 0;
        try {
            java.util.Date date = df.parse(strDate);
            String time = df.format(date);
            Timestamp timestamp = Timestamp.valueOf(strDate);
            timeLong = timestamp.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeLong;
    }

    //Timestamp转String
    public static String timestampToString(long timestamp) {
        Timestamp ts = new Timestamp(timestamp * 1000);
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");//定义格式，不显示毫秒
        String str = df.format(ts);
        return str;
    }


    public static String timestampToString2(long timestamp) {
        Timestamp ts = new Timestamp(timestamp * 1000);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义格式，不显示毫秒
        String str = df.format(ts);
        return str;
    }

    public static String longTimeToString(long timestamp) {
        Timestamp ts = new Timestamp(timestamp);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义格式，不显示毫秒
        String str = df.format(ts);
        return str;
    }

    public static String timestampToString3(long timestamp) {
        Timestamp ts = new Timestamp(timestamp);
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//定义格式，不显示毫秒
        String str = df.format(ts);
        return str;
    }

    //Timestamp转date
    public static Map<Integer, Integer> timestampToDate(long timestamp) {
        Map<Integer, Integer> dateMap = new HashMap<>();
        Timestamp ts = new Timestamp(timestamp * 1000);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义格式，不显示毫秒
        String str = df.format(ts);
        String[] dateStrings = str.split("-");
        dateMap.put(0, Integer.parseInt(dateStrings[0]));
        dateMap.put(1, Integer.parseInt(dateStrings[1]));
        dateMap.put(2, Integer.parseInt(dateStrings[2]));
        return dateMap;
    }

    public static String getInstanceDate() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return df.format(date);
    }

    //判断两个时间戳(Timestamp)是否在同一天
    public static boolean isTheSameDate(Timestamp time1, Timestamp time2) {
        if (time1 != null && time2 != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(time1);
            int y1 = c1.get(Calendar.YEAR);
            int m1 = c1.get(Calendar.MONTH);
            int d1 = c1.get(Calendar.DATE);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(time2);
            int y2 = c2.get(Calendar.YEAR);
            int m2 = c2.get(Calendar.MONTH);
            int d2 = c2.get(Calendar.DATE);
            if (y1 == y2 && m1 == m2 && d1 == d2) {
                return true;
            }
        } else {
            if (time1 == null && time2 == null) {
                return true;
            }
        }
        return false;
    }
}
