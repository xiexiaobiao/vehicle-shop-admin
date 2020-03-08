package com.biao.shop.stock.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.shop.common.dao.ShopItemBrandDao;
import com.biao.shop.common.entity.ShopItemBrandEntity;
import com.biao.shop.stock.service.ShopItemBrandService;
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
 * 商品品牌表 服务实现类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-02-28
 */
@Service
public class ShopItemBrandServiceImpl extends ServiceImpl<ShopItemBrandDao, ShopItemBrandEntity> implements ShopItemBrandService {

    private ShopItemBrandDao itemBrandDao;

    @Autowired
    public ShopItemBrandServiceImpl(ShopItemBrandDao itemBrandDao) {
        this.itemBrandDao = itemBrandDao;
    }

    @Override
    public int createItemBrand(ShopItemBrandEntity itemBrandEntity) {
        itemBrandEntity.setGenerateDate(LocalDateTime.now());
        return itemBrandDao.insert(itemBrandEntity);
    }

    @Override
    public ShopItemBrandEntity getItemBrandById(int id) {
        return itemBrandDao.selectById(id);
    }

    @Override
    public ShopItemBrandEntity getItemBrandByBrandId(String brandId) {
        return itemBrandDao.selectOne(new LambdaQueryWrapper<ShopItemBrandEntity>()
                .eq(ShopItemBrandEntity::getBrandId,brandId ));
    }

    @Override
    public String getMaxBrandId() {
        List<ShopItemBrandEntity> list = itemBrandDao.selectList(new LambdaQueryWrapper<ShopItemBrandEntity>()
                .isNotNull(ShopItemBrandEntity::getBrandId).orderByDesc(ShopItemBrandEntity::getBrandId));
        if (list.size() == 0){
            return null;
        }else {
            return list.stream().limit(1L).collect(Collectors.toList())
                    .get(0).getBrandId();
        }
    }

    // 使用com.baomidou.mybatisplus.pagination的分页实现
    @Override
    public Page<String> listBrand(int current, int size) {
        QueryWrapper<ShopItemBrandEntity> qw = new QueryWrapper<>();
        qw.isNotNull("brand_name");
        Page<ShopItemBrandEntity> itemPage = new Page<>(current,size);
        itemPage = itemBrandDao.selectPage(itemPage,qw);
        Set<String> set = new HashSet<>(16);
        itemPage.getRecords().parallelStream().forEach(item->set.add(item.getBrandName()));
        Page<String> brandPage = new Page<>(current,size);
        BeanUtils.copyProperties(itemPage,brandPage,"records");
        brandPage.setRecords(new ArrayList<>(set));
        return brandPage;
    }
}
