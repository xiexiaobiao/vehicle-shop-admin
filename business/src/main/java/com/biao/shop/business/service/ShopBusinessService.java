package com.biao.shop.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biao.shop.common.bo.OrderBo;
import com.biao.shop.common.dto.OrderDto;
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
public interface ShopBusinessService extends IService<ShopOrderEntity> {
    int saveOrderDTO(OrderDto orderDTO);
    int updateOrderDTO(OrderDto orderDTO);
    List<ShopOrderEntity> listOrder(String condition);
    ShopOrderEntity queryOrder(String orderId);
    boolean checkOrderSaveStatus(String orderUUID);
    Page<ShopOrderEntity> queryOrderPagination(Integer current, Integer size);
    OrderBo getOrderBO(int orderId);
}
