package com.biao.shop.customer.controller;


import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 客户表 前端控制器
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-01-06
 */
@RestController
@RequestMapping("/shop-client-entity")
public class ShopClientController {

    @NacosInjected
    private NamingService namingService;

    @NacosInjected
    private ConfigService configService;

    @RequestMapping("/nacos")
    public List<Instance> getInstance(@PathVariable String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }

}

