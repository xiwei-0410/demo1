package com.mkl.suaggerDemo.service;

import java.util.Map;

/**
 * USER: gll
 * DATE: 2020/4/27
 * TIME: 14:31
 * Describe:
 */

public interface TemplateInter {
    /**
     * 获取acessstoken
     *
     * @param oautodel
     * @return
     */

    String getAccessTokenStr(String oautodel);

    /**
     * 发送到多个手机号
     *
     * @param phones
     * @param param
     * @return
     */

    void sendSmsByPhones(String phones, String... param) throws Exception;

    /**
     * 发送短信到一个号码
     *
     * @param tel
     * @param param
     * @return
     */

    Map<String, String> sendSms(String tel, String... param) throws Exception;
}
