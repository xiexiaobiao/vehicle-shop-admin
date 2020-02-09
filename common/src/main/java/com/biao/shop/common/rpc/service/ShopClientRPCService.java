package com.biao.shop.common.rpc.service;

import com.biao.shop.common.entity.ShopClientEntity;

public interface ShopClientRPCService {

    int addPoint(String uuid,int pointToAdd);

    ShopClientEntity queryById(String uuid);
}
