package com.biao.shop.stock.service;

import com.biao.shop.common.entity.ShopItemEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-01-06
 */
public interface ShopItemService extends IService<ShopItemEntity> {
    ShopItemEntity queryById(String uuid);
}
