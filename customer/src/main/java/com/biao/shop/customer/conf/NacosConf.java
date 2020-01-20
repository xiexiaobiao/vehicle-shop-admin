package com.biao.shop.customer.conf;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.spring.context.annotation.EnableNacos;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.alibaba.nacos.spring.context.annotation.discovery.EnableNacosDiscovery;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

@Configuration
@Configurable
// @EnableNacosConfig启用 Nacos Spring 的配置管理服务
@EnableNacosConfig(globalProperties = @NacosProperties(serverAddr = "127.0.0.1:8848"))
@NacosPropertySource(dataId = "shop",autoRefreshed = true,groupId = "DEFAULT_GROUP")
// @EnableNacosDiscovery 注解开启 Nacos Spring 的服务发现功能
@EnableNacosDiscovery
public class NacosConf {

    // @NacosValue 注解获取nacos配置的属性值
    /*@NacosValue(value = "${useLocalCache:false}", autoRefreshed = true)
    private boolean useLocalCache;*/

    @NacosConfigListener(dataId = "shop",groupId = "DEFAULT_GROUP",timeout = 1500L)
    public void onShop(String name) {
        assert name.equals("xiaobiao");
    }

}
