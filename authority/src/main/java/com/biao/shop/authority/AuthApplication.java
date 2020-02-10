package com.biao.shop.authority;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName AuthApplication
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/9
 * @Version V1.0
 **/
@SpringBootApplication
@MapperScan(basePackages = "com.biao.shop.common.dao")
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class,args);
        System.out.println("Auth Application started.>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
