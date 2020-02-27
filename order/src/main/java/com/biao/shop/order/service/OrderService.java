package com.biao.shop.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.biao.shop.common.dto.OrderDto;
import com.biao.shop.common.entity.ShopOrderEntity;

import java.util.Collection;

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
    int deleteById(int id);
    ShopOrderEntity selectByUuId(String uuid);
    int paidOrder(int orderId,String note);
    int updateOrder(ShopOrderEntity orderEntity);
    int createOrder(ShopOrderEntity orderEntity);
    ShopOrderEntity queryOrder(int id);
    Page<OrderDto> listOrderDTO(Integer current, Integer size, String orderUuid, String clientName, String phone,
                                String vehicleSeries, String vehiclePlate, String generateDateStart,
                                String generateDateEnd, int paidStatus);

}
