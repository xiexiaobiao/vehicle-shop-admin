package com.biao.shop.gateway.conf;

import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * @author: create by xiexiaobiao
 * @version: v1.0
 * @description: com.xmair.me.component
 * @date:2019/9/18
 **/
@Component
public class CustomizedServletRequestListener implements ServletRequestListener {
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("ServletRequestListener requestDestroyed.....");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("ServletRequestListener requestInitialized.....");
    }
}
