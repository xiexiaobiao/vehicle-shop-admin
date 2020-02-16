package com.biao.shop.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.biao.shop.common.entity.ItemListEntity;
import com.biao.shop.common.dao.ItemListDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.shop.order.service.ItemListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单商品明细清单 服务实现类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-01-06
 */
@Service
public class ItemListServiceImpl extends ServiceImpl<ItemListDao, ItemListEntity> implements ItemListService {

    @Autowired
    ItemListDao itemListDao;

    @Override
    public boolean saveDetail(List<ItemListEntity> list) {
        list.forEach(itemListEntity -> itemListDao.insert(itemListEntity));
        return true;
    }

    @Override
    public int deleteDetail() {
        return 0;
    }

    @Override
    public int modifyDetail() {
        return 0;
    }

    @Override
    public List<ItemListEntity> listDetail(String orderUuid) {
        return itemListDao.selectList(
                new LambdaQueryWrapper<ItemListEntity>().eq(ItemListEntity::getOrderUuid,orderUuid));
    }
}
