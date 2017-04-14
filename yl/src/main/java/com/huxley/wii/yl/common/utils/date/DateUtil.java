package com.huxley.wii.yl.common.utils.date;

import com.huxley.wii.yl.common.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期工具类(使用了ThreadLocal获取SimpleDateFormat)
 * Created by huxley on 17/1/14.
 */
public class DateUtil {

    private static Map<String,ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<>();

    public final static String MDHMSS = "MMddHHmmssSSS";
    public final static String YMDHMS = "yyyyMMddHHmmss";
    public final static String YMDHMS_1 = "yyyy-MM-dd HH:mm:ss";
    public final static String YMD = "yyyyMMdd";
    public final static String YMD_ = "yyyy-MM-dd";
    public final static String HMS = "HHmmss";

    /**
     * 根据map中的key得到对应线程的sdf实例
     * @param pattern map中的key
     * @return 该实例
     */
    private static SimpleDateFormat getSdf(final String pattern){
        ThreadLocal<SimpleDateFormat> sdfThread = sdfMap.get(pattern);
        if (sdfThread == null){
            //双重检验,防止sdfMap被多次put进去值
            synchronized (DateUtil.class){
                sdfThread = sdfMap.get(pattern);
                if (sdfThread == null){
                    sdfThread = new ThreadLocal<SimpleDateFormat>(){
                        @Override
                        protected SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(pattern, Constant.LOCALE);
                        }
                    };
                    sdfMap.put(pattern,sdfThread);
                }
            }
        }
        return sdfThread.get();
    }

    /**
     * 按照指定pattern解析日期
     * @param date 要解析的date
     * @param pattern 指定格式
     * @return 解析后date实例
     */
    public static Date parseDate(String date, String pattern){
        if(date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        try {
            return  getSdf(pattern).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 按照指定pattern格式化日期
     * @param date 要格式化的date
     * @param pattern 指定格式
     * @return 解析后格式
     */
    public static String formatDate(Date date,String pattern){
        if (date == null){
            throw new IllegalArgumentException("The date must not be null");
        }else {
            return getSdf(pattern).format(date);
        }
    }

    public static String getCurrentTime(String pattern) {
        return formatDate(new Date(), pattern);
    }
}