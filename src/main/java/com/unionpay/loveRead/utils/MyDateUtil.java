package com.unionpay.loveRead.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MyDateUtil {

    /**
     * 获取n天后的时间
     *
     * @param now
     * @param n
     *
     * @return
     */
    public static Timestamp getEndTime(Timestamp now, int n) {
        Date date = new Date();
        Calendar cc = Calendar.getInstance();
        cc.setTime(now);
        cc.add(Calendar.DAY_OF_MONTH, n);
        date = cc.getTime();
        Timestamp endTime = new Timestamp(date.getTime());
        return endTime;
    }


    /**
     * 字符串转化timestamp
     *
     * @param timestampStr
     *
     * @return
     */
    public static Timestamp str2Timestamp(String timestampStr) {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = Timestamp.valueOf(timestampStr);//转换时间字符串为Timestamp
        return timestamp;
    }

    /**
     * timestamp转化字符串
     * @param timestamp
     * @return
     */
    public static String timestamp2Str(Timestamp timestamp){
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            tsStr = sdf.format(timestamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }

    /**
     * 获取yyyyMMdd格式日期
     *
     * @return
     */
    public static String getYyyyMmDd() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        return sdf.format(c.getTime());
    }

    /**
     * 计算两个时间之间相差的天数
     *
     * @return
     */
    public static int getDaySpace(Timestamp largeTime, Timestamp smallTime) {
        Long timeSpace = largeTime.getTime() - smallTime.getTime();
        Long daySpace = timeSpace / (24 * 60 * 60 * 1000);
        return daySpace.intValue();
    }


    /**
     * 获取今天零点零分零秒的毫秒数
     *
     * @return
     */
    public static long getTodayZero() {
        long current = System.currentTimeMillis();//当前时间毫秒数
        long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
        return zero;
    }

    /**
     * 获取今天23点59分59秒的毫秒数
     *
     * @param zero
     *
     * @return
     */
    public static long getTodayTwelve(long zero) {
        long twelve = zero + 24 * 60 * 60 * 1000 - 1;
        return twelve;
    }


    public static void main(String[] args) {
//        Timestamp now = new Timestamp(System.currentTimeMillis());
//        Timestamp endTime = getEndTime(now,30);
//        System.out.println(endTime);
//        int daySpace = getDaySpace(now,endTime);
//        System.out.println(daySpace);

//        System.out.println(new Timestamp(getTodayZero())); 
//        System.out.println(new Timestamp(getTodayTwelve(getTodayZero()))); 
    }

}
