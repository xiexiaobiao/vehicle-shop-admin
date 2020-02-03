package com.biao.shop.business.impl;

import com.biao.shop.common.entity.ItemListEntity;
import com.biao.shop.common.dao.ItemListDao;
import com.biao.shop.common.service.ItemListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
}
