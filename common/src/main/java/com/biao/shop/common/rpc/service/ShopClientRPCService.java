package com.biao.shop.common.rpc.service;

import com.biao.shop.common.dto.ClientQueryDTO;
import com.biao.shop.common.entity.ShopClientEntity;

import java.util.List;

public interface ShopClientRPCService {

    int addPoint(String uuid,int pointToAdd);

    ShopClientEntity queryById(String uuid);

    List<ShopClientEntity> listByClientDto(ClientQueryDTO clientQueryDTO);
}
