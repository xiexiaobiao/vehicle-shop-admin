package com.biao.shop.stock.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biao.shop.common.entity.ShopItemBrandEntity;
import com.biao.shop.stock.service.ShopItemBrandService;
import org.dromara.soul.client.common.annotation.SoulClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 商品品牌表 前端控制器
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-02-28
 */
@RestController
@RequestMapping("/stock")
public class ShopItemBrandController {
    private ShopItemBrandService itemBrandService;

    @Autowired
    public ShopItemBrandController(ShopItemBrandService itemBrandService) {
        this.itemBrandService = itemBrandService;
    }

    @SoulClient(path = "/vehicle/stock/brandSave", desc = "新增品牌")
    @PostMapping("/brandSave")
    public int createItemBrand(@RequestBody ShopItemBrandEntity itemBrandEntity){
        System.out.println(itemBrandEntity);
        return itemBrandService.createItemBrand(itemBrandEntity);
    }

    @SoulClient(path = "/vehicle/stock/max/brandUId", desc = "查询最大品牌id")
    @GetMapping("/max/brandUId")
    public String getMaxBrandCateId(){
        return itemBrandService.getMaxBrandId();
    }

    @SoulClient(path = "/vehicle/stock/brand/**", desc = "根据id查询")
    @GetMapping("/brand/{id}")
    public ShopItemBrandEntity getItemBrandById(@PathVariable("id") String id){
        return itemBrandService.getItemBrandById(Integer.parseInt(id));
    }

    @SoulClient(path = "/vehicle/stock/brand/", desc = "根据brandId查询")
    @GetMapping("/brand")
    public ShopItemBrandEntity getItemBrandByBrandId(@RequestParam("brandId") String brandId){
        return itemBrandService.getItemBrandByBrandId(brandId);
    }

    @SoulClient(path = "/vehicle/stock/brand/list", desc = "获取品牌列表")
    @GetMapping("/brand/list")
    public Page<String> listBrand(@RequestParam("pageNum") int current, @RequestParam("pageSize")int size){
        return itemBrandService.listBrand(current,size);
    }

}

