package com.biao.shop.customer.nacos;


import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @Classname NacosConfTest
 * @Description  nacos配置的API操作实例
 * @Author KOOL
 * @Date  2020/1/17 22:11
 * @Version 1.0
 **/
@Component
public class NacosConfTest {

    /**@title
    @description ConfigService的使用
    @author KOOL
    @date 2020/1/17 22:16
    @param
    @return
    */
    public void testNacosConfig() throws NacosException {
        String serverAddr = "localhost:8848";
        String dataId = "shop";
        String group = "DEFAULT_GROUP";
        Properties properties = new Properties();
        properties.put("serverAddr", serverAddr);
        ConfigService configService = NacosFactory.createConfigService(properties);
        // 获取配置文件
        String content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);
        // 监听配置变化
        configService.addListener(dataId, group, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("receive:" + configInfo);
            }
        });
        // 发布配置
        configService.publishConfig(dataId,group,content);
        // 查询实例
        NamingService namingService = NamingFactory.createNamingService(System.getProperty("serveAddr"));
        List<Instance> instanceList = namingService.getAllInstances("");
        instanceList.forEach(System.out::println);
    }


}
