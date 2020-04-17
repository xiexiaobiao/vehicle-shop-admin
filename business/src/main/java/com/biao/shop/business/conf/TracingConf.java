package com.biao.shop.business.conf;

import brave.context.slf4j.MDCScopeDecorator;
import brave.propagation.CurrentTraceContext;
import brave.spring.beans.CurrentTraceContextFactoryBean;
import brave.spring.beans.TracingFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.beans.AsyncReporterFactoryBean;
import zipkin2.reporter.beans.OkHttpSenderFactoryBean;
import zipkin2.reporter.okhttp3.OkHttpSender;

import java.util.Arrays;

/**
 * @ClassName TracingConf
 * @Description: TODO
 * @Author Biao
 * @Date 2020/4/16
 * @Version V1.0
 **/
//@Configuration
public class TracingConf {

    private String endpoint = "http://192.168.0.224:9411/api/v2/spans";

    @Value("${dubbo.application.name}")
    private String serviceName;

    /** 使用@Qualifier实现bean依赖注入，即IOC */
    @Bean(name = "tracing")
    TracingFactoryBean tracingFactoryBean(@Qualifier("reporter") AsyncReporter reporter,
                                          @Qualifier("context")CurrentTraceContextFactoryBean context) {
        TracingFactoryBean tracingFactoryBean = new TracingFactoryBean();
        tracingFactoryBean.setLocalServiceName(serviceName);
        tracingFactoryBean.setSpanReporter(reporter);
        tracingFactoryBean.setCurrentTraceContext(context.getObject());
        return  tracingFactoryBean;
    }


    /** 使用@Qualifier实现bean依赖注入，即IOC*/
    @Bean("reporter")
    AsyncReporterFactoryBean asyncReporterFactoryBean(@Qualifier("okHttpSender") OkHttpSender sender){
        AsyncReporterFactoryBean asyncReporterFactoryBean = new AsyncReporterFactoryBean();
        asyncReporterFactoryBean.setSender(sender);
        asyncReporterFactoryBean.setCloseTimeout(500);
        return asyncReporterFactoryBean;
    }

    @Bean("context")
    CurrentTraceContextFactoryBean currentTraceContextFactoryBean(){
        CurrentTraceContextFactoryBean currentTraceContextFactoryBean = new CurrentTraceContextFactoryBean();
        CurrentTraceContext.ScopeDecorator scopeDecorator = MDCScopeDecorator.create();
        currentTraceContextFactoryBean.setScopeDecorators(Arrays.asList(scopeDecorator));
        return currentTraceContextFactoryBean;
    }

    @Bean(name = "okHttpSender")
    OkHttpSenderFactoryBean sender(){
        OkHttpSenderFactoryBean okHttpSenderFactoryBean = new OkHttpSenderFactoryBean();
        okHttpSenderFactoryBean.setEndpoint(endpoint);
        return  okHttpSenderFactoryBean;
    }


}
