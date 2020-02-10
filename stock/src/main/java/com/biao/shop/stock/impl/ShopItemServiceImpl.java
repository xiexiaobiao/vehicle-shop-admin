package com.biao.shop.stock.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.shop.common.entity.ShopItemEntity;
import com.biao.shop.common.dao.ShopItemDao;
import com.biao.shop.stock.service.ShopItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Transactional
    @Override
    public int addItem(ShopItemEntity itemEntity) {
        return shopItemDao.insert(itemEntity);
    }

    @Override
    public Set<String> listBrand() {
        QueryWrapper<ShopItemEntity> qw = new QueryWrapper<>();
        qw.isNotNull("brand");
        Set<String> strings = new HashSet<>(16);
        shopItemDao.selectList(qw).forEach(item -> strings.add(item.getBrand()));
        return strings;
    }

}
