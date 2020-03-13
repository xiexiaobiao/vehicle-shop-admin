package com.biao.shop.order.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biao.shop.common.dao.*;
import com.biao.shop.common.dto.ShopOrderAppDTO;
import com.biao.shop.common.entity.*;
import com.biao.shop.order.manager.OrderManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private ShopClientDao clientDao;

    @Autowired
    public OrderManagerImpl(ShopOrderDao orderDao, ShopItemPictureDao itemPictureDao, ShopItemDao itemDao, ItemListDao listDao, ShopClientDao clientDao) {
        this.orderDao = orderDao;
        this.itemPictureDao = itemPictureDao;
        this.itemDao = itemDao;
        this.listDao = listDao;
        this.clientDao = clientDao;
    }

    @Override
    public Page<ShopOrderAppDTO> listOrderAppDto(int current, int size, int paidStatus,String clientName,String vehiclePlate) {
        // 先查询客户信息
        List<ShopClientEntity> clientEntities = clientDao.selectList(new LambdaQueryWrapper<ShopClientEntity>()
                .like(!StringUtils.isBlank(clientName),ShopClientEntity::getClientName,clientName) //粗暴sql模糊匹配
                .or().like(!StringUtils.isBlank(vehiclePlate),ShopClientEntity::getVehiclePlate,vehiclePlate)); //粗暴sql模糊匹配
        if (clientEntities.size() == 0){
            return null;
        }
        List<String> clientUidS = clientEntities.stream().map(ShopClientEntity::getClientUuid).collect(Collectors.toList());
        // 先使用order page对象
        QueryWrapper<ShopOrderEntity> qw = new QueryWrapper<>();
        Map<String,Object> map = new HashMap<>(1);
//        map.put("order_uuid",orderUuid);
        map.put("is_paid", paidStatus == 2 ? null : paidStatus);
        qw.allEq(true,map,false).orderByDesc("generate_date").in("client_uuid",clientUidS);;
        Page<ShopOrderEntity> orderEntityPage = new Page<>(current,size);
        orderEntityPage = orderDao.selectPage(orderEntityPage,qw);
        List<ShopOrderAppDTO> appDTOList = new ArrayList<>(64);
        // 查询订单
        List<ShopOrderEntity> orderList = orderEntityPage.getRecords();
        orderList.forEach(orderEntity -> {
            ShopOrderAppDTO itemAppDTO = new ShopOrderAppDTO();
            List<ShopOrderAppDTO.Detail> detailList = new ArrayList<>(8);
            itemAppDTO.setDetail(detailList);
            BeanUtils.copyProperties(orderEntity,itemAppDTO);
            // 客户
            ShopClientEntity clientEntity = clientDao.selectOne(new LambdaQueryWrapper<ShopClientEntity>().eq(ShopClientEntity::getClientUuid, orderEntity.getClientUuid()));
            BeanUtils.copyProperties(clientEntity,itemAppDTO,"generateDate","modifyDate");
            // 商品照片
            List<ItemListEntity> list = listDao.selectList(new LambdaQueryWrapper<ItemListEntity>().eq(ItemListEntity::getOrderUuid,orderEntity.getOrderUuid()));
            list.forEach(itemEntity -> {
                ShopOrderAppDTO.Detail detail = new ShopOrderAppDTO.Detail();
                ShopItemEntity item = itemDao.selectOne(new LambdaQueryWrapper<ShopItemEntity>().eq(ShopItemEntity::getItemUuid,itemEntity.getItemUuid()));
                BeanUtils.copyProperties(itemEntity,detail);
                BeanUtils.copyProperties(item,detail);
                // 商品图有多个，只取id最小的
                List<ShopItemPictureEntity> entities = itemPictureDao.selectList(new LambdaQueryWrapper<ShopItemPictureEntity>()
                        .eq(ShopItemPictureEntity::getItemUuid, itemEntity.getItemUuid()).orderByAsc(ShopItemPictureEntity::getId));
                detail.setPicAddr(entities.size()>0 ? entities.get(0).getPicAddr() : "");
                itemAppDTO.getDetail().add(detail);
            });
            appDTOList.add(itemAppDTO);
        });
        // 再将page中的list替换掉,其他信息不变
        Page<ShopOrderAppDTO> itemAppDTOPage = new Page<>(current,size);
        BeanUtils.copyProperties(orderEntityPage,itemAppDTOPage,"records");
        return itemAppDTOPage.setRecords(appDTOList);
    }
}
