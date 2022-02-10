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
    private WebSocket websocket = new WebSocket();

    @RequestMapping("goHtml")
    public String goHtml(){
        return "webSocket.html";
    }

    @RequestMapping("ceshi")
    @ResponseBody
    public Object ceshi(){
        websocket.onMessage("cececececececececece");
        return 50;
    }

    @Scheduled(cron = "0 0 0/5 * * ?")
    @Scheduled(cron = "0 30 2/5 * * ?")
    public void aa(){
        System.out.println("定时时间======"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"==");
    }


}
