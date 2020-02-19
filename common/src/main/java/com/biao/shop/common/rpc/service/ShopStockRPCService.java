package com.biao.shop.common.rpc.service;

import com.biao.shop.common.entity.ShopItemEntity;

public interface ShopStockRPCService {
    ShopItemEntity queryByUuId(String uuid);
    int frozenStock(String itemUuid, int frozenQuantity) throws Exception;
    int decreaseStock(String itemUuid, int decreaseQuantity) throws Exception;
    int unfrozenStock(String itemUuid, int unfrozenQuantity) throws Exception;
}
