package com.biao.shop.business;

import io.lettuce.core.dynamic.annotation.CommandNaming;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Classname com.biao.business.TestMain
 * @Description  todo
 * @Author KOOL
 * @Date  2020/1/5 20:01
 * @Version 1.0
 **/
@SpringBootApplication
@MapperScan(basePackages = "com.biao.shop.common.dao")
@EnableDubbo
public class BusinessApp {
    public static void main(String[] args) {
        SpringApplication.run(BusinessApp.class,args);
        System.out.println("Business Application started.>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
