package com.mkl.websocket;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;

/**
 * bean注入
 * @author mkl
 */

@Configuration
@ConditionalOnWebApplication
public class WebSocketConfig  {

/**
     * 使用boot内置tomcat时需要注入此bean
     * @return
     */

//    @Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();
//    }
//
//
//    @Bean
//    public MySpringConfigurator mySpringConfigurator() {
//        return new MySpringConfigurator();
//    }
}

