package com.biao.shop.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biao.shop.common.entity.ItemListEntity;
import com.biao.shop.common.entity.ShopOrderEntity;
import com.github.pagehelper.PageInfo;

import java.util.Collection;
import java.util.List;

/**
 * @ClassName OrderService
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/15
 * @Version V1.0
 **/
public interface OrderService extends IService<ShopOrderEntity>{

    int deleteOrderByUuid(String uuid);
    int deleteBatchByIds(Collection<Integer> ids);
    int updateOrder(ShopOrderEntity orderEntity);
    int createOrder(ShopOrderEntity orderEntity);
    ShopOrderEntity queryOrder(int id);
    PageInfo<ShopOrderEntity> listOrder(Integer current, Integer size);
    List<ItemListEntity> getOrderList(String orderUid);
}
