package com.biao.shop.stock.rpc;

import com.biao.shop.common.entity.ShopItemEntity;
import com.biao.shop.common.rpc.service.ShopItemRPCService;
import com.biao.shop.stock.service.ShopItemService;
import org.dromara.soul.client.common.annotation.SoulClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName ShopItemRPCServiceImpl
 * @Description: 对外RPC服务接口
 * @Author Biao
 * @Date 2020/2/9
 * @Version V1.0
 **/
public class ShopItemRPCServiceImpl implements ShopItemRPCService {
    @Autowired
    ShopItemService shopItemService;

    @Override
    //@SoulClient(path = "/queryById", desc = "根据用户查询")
    public ShopItemEntity queryById(String uuid){
        return shopItemService.queryById(uuid);
    }
}
