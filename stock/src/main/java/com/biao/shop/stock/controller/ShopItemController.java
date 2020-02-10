package com.biao.shop.stock.controller;


import com.biao.shop.common.entity.ShopItemEntity;
import com.biao.shop.stock.service.ShopItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Set;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-01-06
 */
@RestController
@RequestMapping("/stock")
public class ShopItemController {

    private ShopItemService shopItemService;

    @Autowired
    public ShopItemController(ShopItemService shopItemService){
        this.shopItemService = shopItemService;
    }

    @PostMapping("/item")
    public int addItem(@RequestBody ShopItemEntity itemEntity){
        return shopItemService.addItem(itemEntity);
    }

    @GetMapping("/brand/list")
    public Set<String> listBrand(){
        return shopItemService.listBrand();
    }
}

