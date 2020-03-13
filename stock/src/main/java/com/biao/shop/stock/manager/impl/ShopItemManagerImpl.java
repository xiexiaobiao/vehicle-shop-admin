package com.biao.shop.stock.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.biao.shop.common.dao.ShopItemDao;
import com.biao.shop.common.dao.ShopItemPictureDao;
import com.biao.shop.common.dao.ShopStockDao;
import com.biao.shop.common.dto.ShopItemEntityDto;
import com.biao.shop.common.entity.ShopItemEntity;
import com.biao.shop.common.entity.ShopItemPictureEntity;
import com.biao.shop.common.entity.ShopStockEntity;
import com.biao.shop.stock.manager.ShopItemManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName shopItemManagerImpl
 * @Description: TODO
 * @Author Biao
 * @Date 2020/3/5
 * @Version V1.0
 **/
@Service
public class ShopItemManagerImpl implements ShopItemManager {

    private ShopItemDao itemDao;
    private ShopStockDao stockDao;
    private ShopItemPictureDao itemPictureDao;

    @Autowired
    public ShopItemManagerImpl(ShopItemDao itemDao, ShopStockDao stockDao, ShopItemPictureDao itemPictureDao) {
        this.itemDao = itemDao;
        this.stockDao = stockDao;
        this.itemPictureDao = itemPictureDao;
    }

    @Override
    public ShopItemEntityDto queryByUId(String uid) {
        ShopItemEntity itemEntity = itemDao.selectOne(new LambdaQueryWrapper<ShopItemEntity>().eq(ShopItemEntity::getItemUuid,uid));
        if (Objects.isNull(itemEntity)){
            return null;
        }
        String itemUuid = itemEntity.getItemUuid();
        ShopStockEntity stockEntity = stockDao.selectOne(new LambdaQueryWrapper<ShopStockEntity>().eq(ShopStockEntity::getItemUuid,itemUuid));
        List<ShopItemPictureEntity> list = itemPictureDao.selectList(new LambdaQueryWrapper<ShopItemPictureEntity>().eq(ShopItemPictureEntity::getItemUuid, itemUuid).orderByAsc(ShopItemPictureEntity::getId))
                .parallelStream().limit(1).collect(Collectors.toList());
        String picAddr = list.size() == 0 ? "": list.get(0).getPicAddr();
        ShopItemEntityDto itemEntityDto = new ShopItemEntityDto();
        BeanUtils.copyProperties(itemEntity,itemEntityDto);
        itemEntityDto.setPicAddr(picAddr);
        itemEntityDto.setSales(stockEntity.getSales());
        itemEntityDto.setStock(stockEntity.getQuantity() - stockEntity.getIdStock());
        return itemEntityDto;
    }
}
