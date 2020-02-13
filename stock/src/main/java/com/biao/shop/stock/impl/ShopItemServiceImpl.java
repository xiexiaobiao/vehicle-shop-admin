package com.biao.shop.stock.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.shop.common.dao.ShopItemDao;
import com.biao.shop.common.entity.ShopItemEntity;
import com.biao.shop.stock.service.ShopItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
    public int deleteById(Integer id) {
        return shopItemDao.deleteById(id);
    }

    @Transactional
    @Override
    public int deleteBatchItems(List<Integer> ids) {
        return shopItemDao.deleteBatchIds(ids);
    }

    @Transactional
    @Override
    public int deleteByUid(List<String> uids) {
        return 0;
    }

    @Transactional
    @Override
    public int updateItem(ShopItemEntity itemEntity) {
        return shopItemDao.updateById(itemEntity);
    }

    // fixme 查全表，需要改进！！
    @Override
    public List<String> listCategory(Integer current, Integer size) {
        List<ShopItemEntity> itemlist = shopItemDao.selectList(
                new LambdaQueryWrapper<ShopItemEntity>().isNotNull(ShopItemEntity::getCategory));
        List<String> cateList = new ArrayList<>(64);
        itemlist.parallelStream().forEach(itemEntity -> cateList.add(itemEntity.getCategory()));
        return cateList.stream().distinct().collect(Collectors.toList());
    }

    // 使用com.baomidou.mybatisplus.pagination的分页实现
    @Override
    public Page<String> listBrand(Integer current, Integer size) {
        QueryWrapper<ShopItemEntity> qw = new QueryWrapper<>();
        qw.isNotNull("brand_name");
        Page<ShopItemEntity> itemPage = new Page<>(current,size);
        itemPage = shopItemDao.selectPage(itemPage,qw);
        Set<String> set = new HashSet<>(16);
        itemPage.getRecords().parallelStream().forEach(item->set.add(item.getBrandName()));
        Page<String> brandPage = new Page<>(current,size);
        BeanUtils.copyProperties(itemPage,brandPage,"records");
        brandPage.setRecords(new ArrayList<>(set));
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
    public PageInfo<ShopItemEntity> listItem(Integer current, Integer size,
                                             String itemName,String itemUuid,
                                             String category,String brandName,
                                             int shipment) {
        QueryWrapper<ShopItemEntity> qw = new QueryWrapper<>();
        Map<String,Object> map = new HashMap<>(4);
        map.put("item_uuid",itemUuid);
        map.put("category",category);
        map.put("brand_name",brandName);
        map.put("is_shipment", shipment == 2 ? null:shipment);
        boolean valid = Objects.isNull(itemName); // "item_name" 模糊匹配
        qw.allEq(true,map,false).like(!valid,"item_name",itemName);
        PageHelper.startPage(current,size);
        List<ShopItemEntity> itemEntityList = shopItemDao.selectList(qw);
        return  PageInfo.of(itemEntityList);
    }

}
