package com.biao.shop.stock.controller;


import com.biao.shop.common.entity.ShopItemEntity;
import com.biao.shop.stock.service.ShopItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 库存表 前端控制器
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-01-06
 */
@RestController
@RequestMapping("/stock")
public class ShopStockController {

    @Autowired
    ShopItemService shopItemService;

    // 查询最大的商品编码值
    @GetMapping("/maxItemUuid")
    public String getMaxItemUuid(){
        return shopItemService.getMaxItemUuid();
    }

}

