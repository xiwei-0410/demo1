package com.mkl.util;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ClassName: Holiday
 * @Author: lxh
 * @Description: 节假日
 * @Date: 2022/3/19 17:29
 */
public class HolidayUtils {

    public static void main(String[] args) {
        String aa = "https://fileimg.makalu.cc/daa090a6-3db9-4f8b-ac94-…5b2055b/深基坑开挖支护及降水工程专项施工方案专家论证后的方案2022.3.2011.pdf";
        System.out.println(aa.substring(aa.length()-4,aa.length()));
    }

    /**
     * 方法描述：方法描述：获取节假日 访问接口，根据返回值判断当前日期是否为工作日， 返回结果：检查具体日期是否为节假日，工作日对应结果为 0,
     * 休息日对应结果为 1, 节假日对应的结果为 2； 注意：传入的时间格式为2020-06-25或者20200625
     */
    public static String getHoliday(String time) {
        String dc = "http://tool.bitefu.net/jiari/?d=";
        String httpUrl = new StringBuffer().append(dc).append(time).toString();
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {

        }
        return result;
    }


}


