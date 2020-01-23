package com.biao.shop.gateway.conf;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author: create by xiexiaobiao
 * @version: v1.0
 * @description: com.xmair.me.component
 * @date:2019/9/18
 **/
@Component
public class CustomizedHttpSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("HttpSessionListener sessionCreated....");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("HttpSessionListener sessionDestroyed...");
    }
}
