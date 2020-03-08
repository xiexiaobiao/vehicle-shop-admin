package com.biao.shop.stock.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.shop.common.dao.ShopItemCategoryDao;
import com.biao.shop.common.entity.ShopItemBrandEntity;
import com.biao.shop.common.entity.ShopItemCategoryEntity;
import com.biao.shop.stock.service.ShopItemCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品类别表 服务实现类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-02-28
 */
@Service
public class ShopItemCategoryServiceImpl extends ServiceImpl<ShopItemCategoryDao, ShopItemCategoryEntity> implements ShopItemCategoryService {

    private ShopItemCategoryDao itemCategoryDao;

    @Autowired
    public ShopItemCategoryServiceImpl(ShopItemCategoryDao itemCategoryDao) {
        this.itemCategoryDao = itemCategoryDao;
    }


    @Override
    public int createItemCate(ShopItemCategoryEntity itemCateEntity) {
        itemCateEntity.setGenerateDate(LocalDateTime.now());
        return itemCategoryDao.insert(itemCateEntity);
    }

    @Override
    public ShopItemCategoryEntity getItemCateById(int id) {
        return itemCategoryDao.selectById(id);
    }

    @Override
    public ShopItemCategoryEntity getItemCateByCateId(String cateId) {
        return itemCategoryDao.selectOne(new LambdaQueryWrapper<ShopItemCategoryEntity>()
                .eq(ShopItemCategoryEntity::getCategoryId,cateId ));
    }

    @Override
    public String getMaxCateId() {
        List<ShopItemCategoryEntity> list = itemCategoryDao.selectList(new LambdaQueryWrapper<ShopItemCategoryEntity>()
                .isNotNull(ShopItemCategoryEntity::getCategoryId).orderByDesc(ShopItemCategoryEntity::getCategoryId));
        if (list.size() == 0){
            return null;
        }else {
            return list.stream().limit(1L).collect(Collectors.toList())
                    .get(0).getCategoryId();
        }
    }

    // 使用com.baomidou.mybatisplus.pagination的分页实现
    @Override
    public Page<String> listCate(int current, int size) {
        QueryWrapper<ShopItemCategoryEntity> qw = new QueryWrapper<>();
        qw.isNotNull("category_name");
        Page<ShopItemCategoryEntity> itemPage = new Page<>(current,size);
        itemPage = itemCategoryDao.selectPage(itemPage,qw);
        Set<String> set = new HashSet<>(16);
        itemPage.getRecords().parallelStream().forEach(item->set.add(item.getCategoryName()));
        Page<String> catePage = new Page<>(current,size);
        BeanUtils.copyProperties(itemPage,catePage,"records");
        catePage.setRecords(new ArrayList<>(set));
        return catePage;
    }
}
