package com.biao.shop.stock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname com.biao.business.TestMain
 * @Description  todo
 * @Author KOOL
 * @Date  2020/1/5 20:01
 * @Version 1.0
 **/
@SpringBootApplication
@MapperScan(basePackages = "com.biao.shop.common.dao.*")
public class StockApp {
    public static void main(String[] args) {
        SpringApplication.run(StockApp.class,args);
        System.out.println("Stock Application started.>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
}
