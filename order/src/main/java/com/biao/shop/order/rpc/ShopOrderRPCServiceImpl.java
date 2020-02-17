package com.biao.shop.order.rpc;

import com.biao.shop.common.entity.ItemListEntity;
import com.biao.shop.common.entity.ShopOrderEntity;
import com.biao.shop.common.rpc.service.ShopOrderRPCService;
import com.biao.shop.order.service.ItemListService;
import com.biao.shop.order.service.OrderService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

/**
 * @ClassName ShopOrderRPCServiceImpl
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/16
 * @Version V1.0
 **/
@Service(version = "1.0.0",group = "shop")
public class ShopOrderRPCServiceImpl implements ShopOrderRPCService {
    @Autowired
    ItemListService itemListService;
    @Autowired
    OrderService orderService;

    @Override
    public ShopOrderEntity queryOrder(int id) {
        return orderService.queryOrder(id);
    }

    @Override
    public List<ItemListEntity> getOrderItemList(String orderUid) {
        return itemListService.listDetail(orderUid);
    }

    @Override
    public boolean saveBatchItems(Collection<ItemListEntity> itemListEntities) {
        return itemListService.saveBatch(itemListEntities);
    }
}
