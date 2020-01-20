package com.biao.shop;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.spring.context.annotation.EnableNacos;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname com.biao.business.TestMain
 * @Description  todo
 * @Author KOOL
 * @Date  2020/1/5 20:01
 * @Version 1.0
 **/
@SpringBootApplication
@MapperScan(basePackages = "com.biao.shop.common.dao")
public class GatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class,args);
        System.out.println("Gateway Application started.>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
