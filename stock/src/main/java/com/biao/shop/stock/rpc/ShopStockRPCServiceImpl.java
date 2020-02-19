package com.biao.shop.stock.rpc;

import com.biao.shop.common.entity.ShopItemEntity;
import com.biao.shop.common.rpc.service.ShopStockRPCService;
import com.biao.shop.stock.service.ShopItemService;
import com.biao.shop.stock.service.ShopStockService;
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
    @Autowired
    ShopStockService stockService;

    @Override
    //@SoulClient(path = "/queryById", desc = "根据用户查询")
    public ShopItemEntity queryByUuId(String uuid){
        return shopItemService.queryByUUid(uuid);
    }

    @Override
    public int frozenStock(String itemUuid, int frozenQuantity) throws Exception {
        return stockService.frozenStock(itemUuid, frozenQuantity);
    }

    @Override
    public int decreaseStock(String itemUuid, int decreaseQuantity) throws Exception {
        return stockService.decreaseStock(itemUuid, decreaseQuantity);
    }

    @Override
    public int unfrozenStock(String itemUuid, int unfrozenQuantity) throws Exception {
        return stockService.unfrozenStock(itemUuid, unfrozenQuantity);
    }
}
