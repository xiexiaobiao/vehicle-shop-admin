package com.biao.shop.order;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName OrderApp
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/15
 * @Version V1.0
 **/
@SpringBootApplication
@MapperScan(basePackages = "com.biao.shop.common.dao")
@EnableDubbo
public class OrderApp {
    public static void main(String[] args) {
        SpringApplication.run(OrderApp.class,args);
        System.out.println("Order Application started.>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
