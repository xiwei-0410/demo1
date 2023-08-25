package com.mkl.suaggerDemo.service;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信推送模板方法
 */
public class SmsTemplate {
    public static Map<String, String> templat(String... param) {
        Map<String, String> map = new HashMap<String, String>();
        //这里存放模板参数，如果模板没有参数直接用template_param={}
        if (param.length > 0) {
            for (int i = 0; i < param.length; i++) {
                if (param[i] == null) {
                    param[i] = "";
                }
                int key = i + 1;
                String paramKey = "param" + key;
                map.put(paramKey, param[i]);
            }
            System.out.println(map.toString() + "for==========");

        }
        return map;

    }
}
