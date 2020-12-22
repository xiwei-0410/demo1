package com.wxw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement//开启声明式事务
@SpringBootApplication
@MapperScan("com.wxw.suaggerDemo.mapper")
@EnableCaching
//@ImportResource("classpath:license.xml")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        System.out.println("********启动成功*******\n" +
                "  (_) __ ___   ____ _ \n" +
                "  | |/ _` \\ \\ / / _` |\n" +
                "  | | (_| |\\ V / (_| |\n" +
                " _/ |\\__,_| \\_/ \\__,_|\n" +
                "|__/                  ");
    }

}
