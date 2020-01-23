package com.biao.shop.gateway.conf;

import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author: create by xiexiaobiao
 * @version: v1.0
 * @description: com.xmair.me.component
 * @date:2019/9/18
 **/
@Component
public class CustomizedServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ServletContextListener contextInitialized.....");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ServletContextListener contextDestroyed.....");
    }
}
