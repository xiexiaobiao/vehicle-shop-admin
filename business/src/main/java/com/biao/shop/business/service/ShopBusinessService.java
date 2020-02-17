package com.biao.shop.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biao.shop.common.bo.OrderBO;
import com.biao.shop.common.dto.OrderDTO;
import com.biao.shop.common.entity.ShopClientEntity;
import com.biao.shop.common.entity.ShopOrderEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
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
    int saveOrderUnpaid(OrderDTO orderDTO);
    int saveOrderPaid(OrderDTO orderDTO);
    int paidOrder(String orderId);
    int deleteOrder(String orderId);
    int modifyOrder(ShopOrderEntity order);
    List<ShopOrderEntity> listOrder(String condition);
    ShopOrderEntity queryOrder(String orderId);
    boolean checkOrderSaveStatus(String orderUUID);
    Page<ShopOrderEntity> queryOrderPagination(Integer current, Integer size);
    OrderBO getOrderBO(int orderId);
}
