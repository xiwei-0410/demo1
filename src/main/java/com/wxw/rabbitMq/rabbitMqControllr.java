package com.wxw.rabbitMq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class rabbitMqControllr {
    @Autowired
    RabbitTemplate rabbitTemplate;

    //Direct Exchange
    @RequestMapping("rabbitMq")
    @ResponseBody
    public String  rabbitMq(){
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", "aaaaaaaaaaaaa");
        return "ok";
    }

    //Topic Exchange
    @RequestMapping("rabbitMq1")
    @ResponseBody
    public String  rabbitMq1(){
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("topicExchange", "topic.man", "bbbbbbbbbbbbbb");
        return "ok1";
    }

    //Topic Exchange
    @RequestMapping("rabbitMq2")
    @ResponseBody
    public String  rabbitMq2(){
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("topicExchange", "topic.woman", "ccccccccccccccccccc");
        return "ok2";
    }
}
