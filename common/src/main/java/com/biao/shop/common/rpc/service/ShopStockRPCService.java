package com.biao.shop.common.rpc.service;

import com.biao.shop.common.entity.ShopItemEntity;

public interface ShopStockRPCService {
    ShopItemEntity queryByUuId(String uuid);
}
