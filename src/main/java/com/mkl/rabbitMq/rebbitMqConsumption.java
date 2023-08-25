package com.mkl.rabbitMq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


/**
 * MQ 消费类
 * 监听的队列名称 TestDirectQueue
 * @author mkl
 */
@Component
//@RabbitListener(queues = "topic.man")
@RabbitListener(queues = "fanout.A")
public class rebbitMqConsumption {
    @RabbitHandler
    public void process(String map){
        System.out.println("MQ消费信息=========="+map);
    }
}
