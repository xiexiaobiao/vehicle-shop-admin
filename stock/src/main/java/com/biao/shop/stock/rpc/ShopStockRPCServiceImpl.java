package com.biao.shop.stock.rpc;

import com.biao.shop.common.entity.ShopItemEntity;
import com.biao.shop.common.rpc.service.ShopStockRPCService;
import com.biao.shop.stock.service.ShopItemService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName ShopItemRPCServiceImpl
 * @Description: 对外RPC服务接口
 * @Author Biao
 * @Date 2020/2/9
 * @Version V1.0
 **/
@Service(version = "1.0.0",group = "shop")
public class ShopStockRPCServiceImpl implements ShopStockRPCService {
    @Autowired
    ShopItemService shopItemService;

    @Override
    //@SoulClient(path = "/queryById", desc = "根据用户查询")
    public ShopItemEntity queryByUuId(String uuid){
        return shopItemService.queryByUUid(uuid);
    }
}
