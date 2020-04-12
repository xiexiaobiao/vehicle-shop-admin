package com.biao.shop.business.conf;

import org.apache.dubbo.config.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname DubboConf
 * @Description  API模式配置dubbo的属性
 * @Author KOOL
 * @Date  2020/1/18 9:19
 * @Version 1.0
 **/
@Configuration
public class DubboConf {

    @Bean
    public ProviderConfig providerConfig(){
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setTimeout(1000);
        return providerConfig;
    }


    @Bean
    public ConsumerConfig consumerConfig(){
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setTimeout(1000);
        return consumerConfig;
    }

    @Bean
    public RegistryConfig registryConfig(){
        RegistryConfig registryConfig = new RegistryConfig();
        //registryConfig.setAddress("zookeeper://localhost:2181");
        //registryConfig.setClient("curator");//使用curator连接zk
        return registryConfig;
    }

    //>>>>>>>>> java.lang.IllegalStateException: Duplicate application configs:
    @Bean
    public ApplicationConfig applicationConfig(){
        ApplicationConfig applicationConfig = new ApplicationConfig();
        //applicationConfig.setName("sprintboot-dubbo-provider");
        return applicationConfig;
    }

    //同时支持多个协议，需使用多个ProtocolConfig
    @Bean
    public ProtocolConfig protocolConfig(){
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setPort(1099);
        protocolConfig.setName("rmi");
        return protocolConfig;
    }
}
