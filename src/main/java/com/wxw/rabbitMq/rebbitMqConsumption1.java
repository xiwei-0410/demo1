package com.wxw.rabbitMq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


/**
 * MQ 消费类
 * 监听的队列名称 TestDirectQueue
 * @author wxw
 */
@Component
//@RabbitListener(queues = "topic.woman")
@RabbitListener(queues = "fanout.B")
public class rebbitMqConsumption1 {
    @RabbitHandler
    public void process(String map){
        System.out.println("MQ消费信息=========="+map);
    }
}
