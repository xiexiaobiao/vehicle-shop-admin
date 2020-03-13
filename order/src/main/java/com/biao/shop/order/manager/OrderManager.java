package com.biao.shop.order.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biao.shop.common.dto.ShopOrderAppDTO;

public interface OrderManager {
    Page<ShopOrderAppDTO> listOrderAppDto(int current, int size, int paidStatus,String clientName,String vehiclePlate);
}
