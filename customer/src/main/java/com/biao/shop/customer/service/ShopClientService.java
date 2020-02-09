package com.biao.shop.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biao.shop.common.entity.ShopClientEntity;

import java.util.List;

/**
 * <p>
 * 客户表 服务类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-01-06
 */
public interface ShopClientService extends IService<ShopClientEntity> {
    int addClient(ShopClientEntity clientEntity);
    int deleteClient(String uuid);
    int modifyClient(ShopClientEntity clientEntity);
    List<ShopClientEntity> queryClient(String condition);
    int addPoint(String uuid, int pointToAdd);
    ShopClientEntity queryById(String uuid);
}
