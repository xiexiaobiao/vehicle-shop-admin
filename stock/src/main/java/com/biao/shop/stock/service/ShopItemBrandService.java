package com.biao.shop.stock.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biao.shop.common.entity.ShopItemBrandEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品品牌表 服务类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-02-28
 */
public interface ShopItemBrandService extends IService<ShopItemBrandEntity> {
    int createItemBrand(ShopItemBrandEntity itemBrandEntity);
    ShopItemBrandEntity getItemBrandById(int id);
    ShopItemBrandEntity getItemBrandByBrandId(String brandId);
    String getMaxBrandId();
    Page<String> listBrand(int current, int size);
}
