package com.biao.shop.stock.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.biao.shop.common.entity.ShopItemCategoryEntity;

/**
 * <p>
 * 商品类别表 服务类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-02-28
 */
public interface ShopItemCategoryService extends IService<ShopItemCategoryEntity> {
    int createItemCate(ShopItemCategoryEntity itemCateEntity);
    ShopItemCategoryEntity getItemCateById(int id);
    ShopItemCategoryEntity getItemCateByCateId(String cateId);
    String getMaxCateId();
    Page<String> listCate(int current, int size);
}
