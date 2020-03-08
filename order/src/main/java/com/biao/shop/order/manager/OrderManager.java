package com.biao.shop.order.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biao.shop.common.dto.ShopItemAppDTO;

public interface OrderManager {
    Page<ShopItemAppDTO> listItemAppDto(int current, int size,int paidStatus);
}
