package com.biao.shop.gateway.conf;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.RequestHandledEvent;

/**
 * @author: create by xiexiaobiao
 * @version: v1.0
 * @description: com.xmair.me.component
 * @date:2019/9/18
 **/
@Component
public class CustomizedApplicationListener implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
       if (event instanceof ContextStartedEvent){
           System.out.println("ApplicationListener ContextStartedEvent .... ");
       }else if (event instanceof ContextRefreshedEvent){
           System.out.println("ApplicationListener ContextRefreshedEvent .... ");
       }else if (event instanceof ContextStoppedEvent){
           System.out.println("ApplicationListener ContextStoppedEvent .... ");
       }else if (event instanceof ContextClosedEvent){
           System.out.println("ApplicationListener ContextClosedEvent .... ");
       }else if (event instanceof RequestHandledEvent){
           System.out.println("ApplicationListener RequestHandledEvent .... ");
       }else {

       }
    }
}
