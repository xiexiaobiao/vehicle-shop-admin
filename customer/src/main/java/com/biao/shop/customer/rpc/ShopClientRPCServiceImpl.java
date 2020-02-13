package com.biao.shop.customer.rpc;

import com.biao.shop.common.entity.ShopClientEntity;
import com.biao.shop.common.rpc.service.ShopClientRPCService;
import com.biao.shop.customer.service.ShopClientService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName ShopClientDubboServiceImpl
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/7
 * @Version V1.0
 **/
//
@Service(version = "1.0.0")
public class ShopClientRPCServiceImpl implements ShopClientRPCService {

    private ShopClientService shopClientService;

    @Autowired
    public ShopClientRPCServiceImpl(ShopClientService shopClientService){
        this.shopClientService = shopClientService;
    }

    @Override
    public int addPoint(String uuid, int pointToAdd) {
        return shopClientService.addPoint(uuid,pointToAdd);
    }

    @Override
    public ShopClientEntity queryById(String uuid) {
        return shopClientService.queryById(uuid);
    }
}