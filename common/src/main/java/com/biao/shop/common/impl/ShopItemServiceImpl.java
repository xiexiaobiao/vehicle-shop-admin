package com.biao.shop.common.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biao.shop.common.dao.ShopClientDao;
import com.biao.shop.common.entity.ShopItemEntity;
import com.biao.shop.common.dao.ShopItemDao;
import com.biao.shop.common.service.ShopItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-01-06
 */
@Service
public class ShopItemServiceImpl extends ServiceImpl<ShopItemDao, ShopItemEntity> implements ShopItemService {

    @Autowired
    private ShopItemDao  shopItemDao;

    @Override
    public ShopItemEntity queryById(String uuid) {
        QueryWrapper<ShopItemEntity> qw = new QueryWrapper<>();
        qw.eq(true,"uuid",uuid);
        return shopItemDao.selectOne(qw);
    }
}
