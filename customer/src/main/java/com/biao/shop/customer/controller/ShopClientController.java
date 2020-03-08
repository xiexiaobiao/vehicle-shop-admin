package com.biao.shop.customer.controller;


import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.biao.shop.common.entity.ShopClientEntity;
import com.biao.shop.common.enums.RespStatusEnum;
import com.biao.shop.common.response.ObjectResponse;
import com.biao.shop.customer.impl.ShopClientServiceImpl;
import com.biao.shop.customer.service.ShopClientService;
import com.biao.shop.customer.nacos.NacosConfTest;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.dromara.soul.client.common.annotation.SoulClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/client")
public class ShopClientController {
    private final Logger logger = LoggerFactory.getLogger(ShopClientController.class);

    /////////////// nacos 测试使用
    @Autowired
    NacosConfTest nacosConfTest;

    @NacosInjected
    private NamingService namingService;

    @NacosInjected
    private ConfigService configService;

    @RequestMapping("/nacos/test")
    public void getInstance() throws NacosException {
        nacosConfTest.testNacosConfig();
    }

    @GetMapping("/nacos/service/{name}")
    public List<Instance> getInstance(@PathVariable(name = "name") String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }
    ////////////////////

    private ShopClientService clientService;

    @Autowired
    public ShopClientController(ShopClientService clientService){
        this.clientService = clientService;
    }

    @SoulClient(path = "/vehicle/client/list", desc = "获取客户列表")
    @GetMapping("/list")
    public ObjectResponse<PageInfo<ShopClientEntity>> listClient(@RequestParam("pageNum")Integer current, @RequestParam("pageSize")Integer size,
                                                 @RequestParam(value = "clientUuid",required = false)String clientUuid,
                                                 @RequestParam(value = "name",required = false)String name,
                                                 @RequestParam(value = "vehiclePlate",required = false)String vehiclePlate,
                                                 @RequestParam(value = "phone",required = false)String phone){
        PageInfo<ShopClientEntity> pageInfo = clientService.listClient( current,size,clientUuid,name,vehiclePlate,phone);
        ObjectResponse<PageInfo<ShopClientEntity>> response = new ObjectResponse<>();
            response.setCode(RespStatusEnum.SUCCESS.getCode());
            response.setMessage(RespStatusEnum.SUCCESS.getMessage());
            response.setData(pageInfo);
        return response;
    }

    @SoulClient(path = "/vehicle/client/**", desc = "查询一个客户")
    @GetMapping("/{id}")
    public ShopClientEntity queryById(@PathVariable("id") int id){
        return clientService.queryById(id);
    }

    @SoulClient(path = "/vehicle/client/uid", desc = "uid查询一个客户")
    @GetMapping("/uid")
    public ObjectResponse<ShopClientEntity> queryByUuId(@RequestParam("uid") String uid){
        ShopClientEntity clientEntity = clientService.queryByUuId(uid);
        ObjectResponse<ShopClientEntity> response = new ObjectResponse<>();
        response.setCode(RespStatusEnum.SUCCESS.getCode());
        response.setMessage(RespStatusEnum.SUCCESS.getMessage());
        response.setData(clientEntity);
        return response;
    }

    @SoulClient(path = "/vehicle/client/update", desc = "更新一个客户")
    @PostMapping("/update")
    public int listClient(@RequestBody ShopClientEntity client){
        return clientService.updateClient(client);
    }

    @SoulClient(path = "/vehicle/client/create", desc = "创建一个客户")
    @PostMapping("/create")
    public ObjectResponse<String> createClient(@RequestBody ShopClientEntity client){
        ObjectResponse<String> response = new ObjectResponse<>();
        int result =  clientService.createClient(client);
        if (StringUtils.isBlank(String.valueOf(result))){
            response.setCode(RespStatusEnum.FAIL.getCode());
            response.setMessage(RespStatusEnum.FAIL.getMessage());
        }else {
            response.setCode(RespStatusEnum.SUCCESS.getCode());
            response.setMessage(RespStatusEnum.SUCCESS.getMessage());
            response.setData(String.valueOf(result));
        }
        return response;
    }

    @SoulClient(path = "/vehicle/client/plates", desc = "获取车牌列表")
    @GetMapping("/plates")
    public List<String> listPlate(){
        return clientService.listPlate();
    }

    @SoulClient(path = "/vehicle/client/del", desc = "删除用户")
    @GetMapping("/del")
    public int delClient(@RequestParam("ids") String ids){
        if (ids.contains(",")){
            List<Integer> list = new ArrayList<>(8);
            String[] strings = ids.split(",");
            for (int i = 0; i < strings.length; i++) {
                list.add(Integer.valueOf(strings[i]));
            }
            return clientService.deleteBatchById(list);
        }
        return clientService.deleteById(Integer.parseInt(ids));
    }

    @SoulClient(path = "/vehicle/client/maxUid", desc = "获取最大客户uid")
    @GetMapping("/maxUid")
    public ObjectResponse<String> getMaxClientUuId(){
        ObjectResponse<String> response = new ObjectResponse<>();
        String maxId = clientService.getMaxClientUuId();
        if (StringUtils.isBlank(maxId)){
            response.setCode(RespStatusEnum.FAIL.getCode());
            response.setMessage(RespStatusEnum.FAIL.getMessage());
        }else {
            response.setCode(RespStatusEnum.SUCCESS.getCode());
            response.setMessage(RespStatusEnum.SUCCESS.getMessage());
            response.setData(maxId);
        }
        return response;
    }

}

