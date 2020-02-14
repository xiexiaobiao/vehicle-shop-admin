package com.biao.shop.customer;

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
//@DubboComponentScan(basePackages = "com.biao.shop.customer.impl")
//@EnableDubbo 包含 @EnableDubboConfig 和 @DubboComponentScan
@EnableDubbo //不要使用basePackages属性，否则无法被服务发现
@EnableNacos(globalProperties = @NacosProperties(serverAddr = "127.0.0.1:8848"))
public class CustomerApp {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApp.class,args);
        System.out.println("Customer Application started.>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
