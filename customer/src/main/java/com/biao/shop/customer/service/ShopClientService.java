package com.biao.shop.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biao.shop.common.entity.ShopClientEntity;
import com.biao.shop.common.entity.ShopItemEntity;
import com.github.pagehelper.PageInfo;

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
    int createClient(ShopClientEntity clientEntity);
    int deleteById(int id);
    int deleteByUUid(String uuid);
    int updateClient(ShopClientEntity clientEntity);
    List<ShopClientEntity> queryClient(String condition);
    int addPoint(String uuid, int pointToAdd);
    ShopClientEntity queryByUuId(String uuid);
    ShopClientEntity queryById(int id);
    PageInfo<ShopClientEntity> listClient(Integer current, Integer size,String clientUuid,
                                            String name,String vehiclePlate,String phone);
}
