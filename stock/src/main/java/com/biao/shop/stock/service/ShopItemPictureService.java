package com.biao.shop.stock.service;

import com.biao.shop.common.entity.ShopItemPictureEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 商品图片 服务类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-02-27
 */
public interface ShopItemPictureService extends IService<ShopItemPictureEntity> {

    List<ShopItemPictureEntity> listItemPictures(String itemUuid);
    int saveItemPictures(Collection<ShopItemPictureEntity> itemPictureEntities);

}
