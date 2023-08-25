package com.mkl.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * java日期转cron表达式工具类
 */
public class CronUtils {

    /**
     * 每年时间format格式
     */
    private static final String DATEFORMAT_YEAR = "ss mm HH dd MM ? yyyy";

    /**
     * 每天时间format格式
     */
    private static final String DATEFORMAT_EVERYDAY = "ss mm HH * * ?";

    /**
     * 每周时间format格式
     */
    private static final String SUNDAY = "ss mm HH ? * 1";
    private static final String MONDAY = "ss mm HH ? * 2";
    private static final String TUESDAY = "ss mm HH ? * 3";
    private static final String WEDNESDAY = "ss mm HH ? * 4";
    private static final String THURSDAY = "ss mm HH ? * 5";
    private static final String FRIDAY = "ss mm HH ? * 6";
    private static final String SATURADY = "ss mm HH ? * 7";

    public static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }
    /**
     * 时间转换时间表达式
     */
    public static String getCron(Date date, String dateFormat) {
        return formatDateByPattern(date, dateFormat);
    }

    public static void main(String[] args)throws Exception{
        String cron = getCron(new Date(), SUNDAY);
        System.out.println("每周六执行"+cron);

        String cron1 = getCron(new Date(), DATEFORMAT_EVERYDAY);
        System.out.println("每天执行"+cron1);

        String cron2 = getCron(new Date(), DATEFORMAT_YEAR);
        System.out.println("执行一次"+cron2);

    }
}
