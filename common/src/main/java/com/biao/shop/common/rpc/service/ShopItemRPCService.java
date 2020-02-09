package com.biao.shop.common.rpc.service;

import com.biao.shop.common.entity.ShopItemEntity;

public interface ShopItemRPCService {
    ShopItemEntity queryById(String uuid);
}
