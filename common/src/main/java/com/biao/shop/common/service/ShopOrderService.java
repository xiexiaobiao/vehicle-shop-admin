package com.biao.shop.common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biao.shop.common.bo.OrderBO;
import com.biao.shop.common.entity.ShopOrderEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-01-06
 */
public interface ShopOrderService extends IService<ShopOrderEntity> {
    int saveOrderUnpaid(OrderBO order);
    int saveOrderPaid(OrderBO order);
    int paidOrder(String orderId);
    int deleteOrder(String orderId);
    int modifyOrder(ShopOrderEntity order);
    List<ShopOrderEntity> listOrder(String condition);
    ShopOrderEntity queryOrder(String orderId);
    boolean checkOrderSaveStatus(String orderUUID);

    Page<ShopOrderEntity> queryOrderPagination(Integer current, Integer size);
}
