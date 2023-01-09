package com.wxw.suaggerDemo.controller;

import com.wxw.websocket.WebSocket;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wxw
 */

@RequestMapping("/weixin/scan/")
@Controller
@EnableScheduling
@Configuration
public class weixnTest {
    private static int _1MB = 1024 * 1024;
    private WebSocket websocket = new WebSocket();

    @RequestMapping("goHtml")
    public String goHtml(){
        return "webSocket.html";
    }

    @RequestMapping("goHtml1")
    public String goHtml1(){
        return "webSocket1.html";
    }


    @Scheduled(cron = "0 0 0/5 * * ?")
    @Scheduled(cron = "0 30 2/5 * * ?")
    public void aa(){
        System.out.println("定时时间======"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"==");
    }

    @RequestMapping("saoyisao")
    public String saoyisao(){
        return  "saoyisao.html";
    }

    @RequestMapping("/ceshijvm")
    @ResponseBody
    public Object ceshijvm(){
        for (int i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    byte[] allocation1 = new byte[2 * _1MB];
                    while (true) {
                        System.out.println("============打印=========="+allocation1);
                    }
                }
            }).start();
        }
        return 1;
    }


}
