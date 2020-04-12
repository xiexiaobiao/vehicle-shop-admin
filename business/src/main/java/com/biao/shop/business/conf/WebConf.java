package com.biao.shop.business.conf;

import com.biao.shop.common.aspectj.IdempotentInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @ClassName WebConf
 * @Description: TODO
 * @Author Biao
 * @Date 2020/4/11
 * @Version V1.0
 **/
@Configuration
public class WebConf implements WebMvcConfigurer {

    @Resource
    IdempotentInterceptor idempotentInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(idempotentInterceptor);
    }
}
