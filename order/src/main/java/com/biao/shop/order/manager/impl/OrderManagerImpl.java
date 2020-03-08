package com.biao.shop.order.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biao.shop.common.dao.ItemListDao;
import com.biao.shop.common.dao.ShopItemDao;
import com.biao.shop.common.dao.ShopItemPictureDao;
import com.biao.shop.common.dao.ShopOrderDao;
import com.biao.shop.common.dto.OrderDto;
import com.biao.shop.common.dto.ShopItemAppDTO;
import com.biao.shop.common.entity.ItemListEntity;
import com.biao.shop.common.entity.ShopItemEntity;
import com.biao.shop.common.entity.ShopItemPictureEntity;
import com.biao.shop.common.entity.ShopOrderEntity;
import com.biao.shop.order.manager.OrderManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OrderManagerImpl
 * @Description: TODO
 * @Author Biao
 * @Date 2020/3/6
 * @Version V1.0
 **/
@Service
public class OrderManagerImpl implements OrderManager {

    private ShopOrderDao orderDao;
    private ShopItemPictureDao itemPictureDao;
    private ShopItemDao itemDao;
    private ItemListDao listDao;

    @Autowired
    public OrderManagerImpl(ShopOrderDao orderDao, ShopItemPictureDao itemPictureDao, ShopItemDao itemDao, ItemListDao listDao) {
        this.orderDao = orderDao;
        this.itemPictureDao = itemPictureDao;
        this.itemDao = itemDao;
        this.listDao = listDao;
    }

    @Override
    public Page<ShopItemAppDTO> listItemAppDto(int current,int size,int paidStatus) {
        // 先使用order page对象
        QueryWrapper<ShopOrderEntity> qw = new QueryWrapper<>();
        Map<String,Object> map = new HashMap<>(2);
//        map.put("order_uuid",orderUuid);
        map.put("is_paid", paidStatus == 2 ? null : paidStatus);
        qw.allEq(true,map,false).orderByDesc("generate_date");
        Page<ShopOrderEntity> orderEntityPage = new Page<>(current,size);
        orderEntityPage = orderDao.selectPage(orderEntityPage,qw);
        List<ShopItemAppDTO> appDTOList = new ArrayList<>(64);
        List<ShopOrderEntity> orderList = orderEntityPage.getRecords();
        orderList.forEach(orderEntity -> {
            ShopItemAppDTO itemAppDTO = new ShopItemAppDTO();
            List<ShopItemAppDTO.Detail> detailList = new ArrayList<>(8);
            itemAppDTO.setDetail(detailList);
            BeanUtils.copyProperties(orderEntity,itemAppDTO);
            List<ItemListEntity> list = listDao.selectList(new LambdaQueryWrapper<ItemListEntity>().eq(ItemListEntity::getOrderUuid,orderEntity.getOrderUuid()));
            list.forEach(itemEntity -> {
                ShopItemAppDTO.Detail detail = new ShopItemAppDTO.Detail();
                ShopItemEntity item = itemDao.selectOne(new LambdaQueryWrapper<ShopItemEntity>().eq(ShopItemEntity::getItemUuid,itemEntity.getItemUuid()));
                BeanUtils.copyProperties(itemEntity,detail);
                BeanUtils.copyProperties(item,detail);
                // 商品图有多个，只取id最小的
                detail.setPicAddr(itemPictureDao.selectList(new LambdaQueryWrapper<ShopItemPictureEntity>()
                        .eq(ShopItemPictureEntity::getItemUuid,itemEntity.getItemUuid()).orderByAsc(ShopItemPictureEntity::getId))
                        .get(0).getPicAddr());
                itemAppDTO.getDetail().add(detail);
            });
            appDTOList.add(itemAppDTO);
        });
        // 再将page中的list替换掉,其他信息不变
        Page<ShopItemAppDTO> itemAppDTOPage = new Page<>(current,size);
        BeanUtils.copyProperties(orderEntityPage,itemAppDTOPage,"records");
        return itemAppDTOPage.setRecords(appDTOList);
    }
}
