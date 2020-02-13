package com.biao.shop.stock.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.shop.common.entity.ShopItemEntity;
import com.biao.shop.common.dao.ShopItemDao;
import com.biao.shop.common.entity.ShopOrderEntity;
import com.biao.shop.stock.service.ShopItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
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
    public ShopItemEntity queryById(String id) {
        return shopItemDao.selectById(id);
    }

    @Override
    public ShopItemEntity queryByUUid(String uuid) {
        QueryWrapper<ShopItemEntity> qw = new QueryWrapper<>();
        qw.eq(true,"uuid",uuid);
        return shopItemDao.selectOne(qw);
    }

    @Transactional
    @Override
    public int addItem(ShopItemEntity itemEntity) {
        return shopItemDao.insert(itemEntity);
    }

    @Transactional
    @Override
    public int updateItem(ShopItemEntity itemEntity) {
        return shopItemDao.updateById(itemEntity);
    }

    // 使用com.baomidou.mybatisplus.pagination的分页实现
    @Override
    public Page<String> listBrand(Integer current, Integer size) {
        QueryWrapper<ShopItemEntity> qw = new QueryWrapper<>();
        qw.isNotNull("brand_name");
        Page<ShopItemEntity> itemPage = new Page<>(current,size);
        itemPage = shopItemDao.selectPage(itemPage,qw);
        Set<String> stringSet = new HashSet<>(32);
        itemPage.getRecords().forEach(item->stringSet.add(item.getBrand()));
        List<String> list = new ArrayList<>(stringSet);
        Page<String> brandPage = new Page<>(current,size);
        BeanUtils.copyProperties(itemPage,brandPage,"records");
        brandPage.setRecords(list);
        return brandPage;
    }

    // 使用com.baomidou.mybatisplus.pagination的分页实现
   /* @Override
    public Page<ShopItemEntity> listItem(Integer current, Integer size) {
        QueryWrapper<ShopItemEntity> qw = new QueryWrapper<>();
        qw.isNotNull("uuid");
        Page<ShopItemEntity> shopItemEntityPage = new Page<>(current,size);
        Page<ShopItemEntity> page = shopItemDao.selectPage(shopItemEntityPage,qw);
        return page;
    }*/

    // 使用com.github.pagehelper 实现
    // 只有紧跟在PageHelper.startPage方法后的第一个Mybatis的查询（Select）方法会被分页!!!!
    @Override
    public PageInfo<ShopItemEntity> listItem(Integer current, Integer size) {
        QueryWrapper<ShopItemEntity> qw = new QueryWrapper<>();
        qw.isNotNull("uuid");
        PageHelper.startPage(current,size);
        List<ShopItemEntity> itemEntityList = shopItemDao.selectList(qw);
        return  PageInfo.of(itemEntityList);
    }

}
