package com.biao.shop.authority.conf;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConf {

    // 另一种注入CorsFilter的方式
    /*@Bean
    public FilterRegistrationBean<CorsFilter> filterRegistrationBean(){
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean();
        bean.setFilter(new CorsFilter());
        bean.addUrlPatterns("/*");
        return bean;
    }*/
}
