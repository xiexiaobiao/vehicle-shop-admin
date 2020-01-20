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
    int saveOrder(OrderBO order);
    int deleteOrder(String uuid);
    int modifyOrder(ShopOrderEntity order);
    List<ShopOrderEntity> queryOrder(String condition);
    boolean checkOrderSaveStatus(String orderUUID);

    Page<ShopOrderEntity> queryOrderPagination(Integer current, Integer size);
}
