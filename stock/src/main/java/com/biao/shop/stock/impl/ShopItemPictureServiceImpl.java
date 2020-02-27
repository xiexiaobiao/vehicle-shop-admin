package com.biao.shop.stock.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.biao.shop.common.entity.ShopItemPictureEntity;
import com.biao.shop.common.dao.ShopItemPictureDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.shop.stock.service.ShopItemPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 商品图片 服务实现类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-02-27
 */
@Service
public class ShopItemPictureServiceImpl extends ServiceImpl<ShopItemPictureDao, ShopItemPictureEntity> implements ShopItemPictureService {

    private ShopItemPictureDao itemPictureDao;

    @Autowired
    public ShopItemPictureServiceImpl(ShopItemPictureDao itemPictureDao) {
        this.itemPictureDao = itemPictureDao;
    }

    @Override
    public List<ShopItemPictureEntity> listItemPictures(String itemUuid) {
        return itemPictureDao.selectList(new LambdaQueryWrapper<ShopItemPictureEntity>()
                .eq(ShopItemPictureEntity::getItemUuid,itemUuid).orderByAsc(ShopItemPictureEntity::getId));
    }

    @Override
    public int saveItemPictures(Collection<ShopItemPictureEntity> itemPictureEntities) {
        itemPictureEntities.forEach(item->itemPictureDao.insert(item));
        return 1;
    }
}
