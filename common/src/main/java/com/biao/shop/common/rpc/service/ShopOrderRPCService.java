package com.biao.shop.common.rpc.service;

import com.biao.shop.common.entity.ItemListEntity;
import com.biao.shop.common.entity.ShopOrderEntity;

import java.util.Collection;
import java.util.List;

public interface ShopOrderRPCService {
    ShopOrderEntity queryOrder(int id);
    List<ItemListEntity> getOrderItemList(String orderUid);
    boolean saveBatchItems(Collection<ItemListEntity> itemListEntities);
    ShopOrderEntity selectByUuId(String uuid);
    int deleteItemListByOrderUid(String orderUuid);
}
