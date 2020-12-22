package com.wxw.suaggerDemo.controller;

import com.wxw.websocket.WebSocket;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wxw
 */
@RequestMapping("/weixin/scan/")
@Controller
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
}
