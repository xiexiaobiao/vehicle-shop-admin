package com.biao.shop.stock.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biao.shop.common.entity.ShopItemEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-01-06
 */
public interface ShopItemService extends IService<ShopItemEntity> {

    ShopItemEntity queryById(String id);

    ShopItemEntity queryByUUid(String uuid);

    int addItem(ShopItemEntity itemEntity);

    int deleteById(Integer id);

    int deleteBatchItems(List<Integer> ids);

    int deleteByUid(List<String> uids);

    int updateItem(ShopItemEntity itemEntity);

    Page<String> listBrand(Integer current, Integer size);

    List<String> listCategory(Integer current, Integer size);

    PageInfo<ShopItemEntity> listItem(Integer current, Integer size,
                                      String itemName,String itemUuid,
                                      String category,String brandName,
                                      int shipment);
}
