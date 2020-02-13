package com.biao.shop.stock.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biao.shop.common.entity.ShopItemEntity;
import com.biao.shop.stock.service.ShopItemService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.dromara.soul.client.common.annotation.SoulClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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


    @SoulClient(path = "/vehicle/stock/item/update", desc = "更新一个商品")
    @PostMapping("/item/update")
    public int updateItem(@RequestBody String jsonStr) {
        System.out.println(jsonStr);
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        ShopItemEntity itemEntity = new ShopItemEntity();
        itemEntity.setIdItem((Integer) jsonObject.get("idItem"));
        String brandName = (String) jsonObject.get("brandName");
        itemEntity.setBrand(StringUtils.isEmpty(brandName)? null : brandName);
        itemEntity.setCategory((String) jsonObject.get("category"));//
        itemEntity.setName((String) jsonObject.get("name"));//
        String purPrice = (String) jsonObject.get("purchasePrice");
        itemEntity.setPurchasePrice( StringUtils.isEmpty(purPrice)? new BigDecimal("0.00") : new BigDecimal(purPrice));
        String price = (String) jsonObject.get("price");
        itemEntity.setSellPrice( StringUtils.isEmpty(price)? new BigDecimal("0.00") : new BigDecimal(price));
        String specification = (String) jsonObject.get("specification");
        itemEntity.setSpecification(StringUtils.isEmpty(specification)? null : specification);
        itemEntity.setUuid((String) jsonObject.get("productSn"));//
        String description = (String) jsonObject.get("description");
        itemEntity.setDescription(StringUtils.isEmpty(description)? null : description);
        Boolean shipment = (Boolean) jsonObject.get("shipment");
        itemEntity.setShipment(shipment);
        return shopItemService.updateItem(itemEntity);
    }

    @SoulClient(path = "/vehicle/stock/item/one/**", desc = "查询一个商品")
    @GetMapping("/item/one/{id}")
    public ShopItemEntity queryById(@PathVariable("id") String id) {
        return shopItemService.queryById(id);
    }
    /*@PathVariable其他格式的：*/
    //@GetMapping("/path/{id}/name")
    //@SoulClient(path = "/test/path/**/name", desc = "test restful风格支持")

    @SoulClient(path = "/vehicle/stock/item/product", desc = "新增商品")
    @PostMapping("/item/product")
    public int addItem(@RequestBody String jsonStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        ShopItemEntity itemEntity = new ShopItemEntity();
        String brand = (String) jsonObject.get("brandName");
        itemEntity.setBrand(StringUtils.isEmpty(brand)? null : brand);
        itemEntity.setCategory((String) jsonObject.get("category"));//
        itemEntity.setName((String) jsonObject.get("name"));//
        String purPrice = (String) jsonObject.get("purchasePrice");
        itemEntity.setPurchasePrice( StringUtils.isEmpty(purPrice)? new BigDecimal("0.00") : new BigDecimal(purPrice));
        String price = (String) jsonObject.get("price");
        itemEntity.setSellPrice( StringUtils.isEmpty(price)? new BigDecimal("0.00") : new BigDecimal(price));
        String specification = (String) jsonObject.get("specification");
        itemEntity.setSpecification(StringUtils.isEmpty(specification)? null : specification);
        itemEntity.setUuid((String) jsonObject.get("productSn"));//
        String description = (String) jsonObject.get("description");
        itemEntity.setDescription(StringUtils.isEmpty(description)? null : description);
        Boolean shipment = ((Integer) jsonObject.get("shipment") == 1);
        itemEntity.setShipment(shipment);
        return shopItemService.addItem(itemEntity);
    }

    @SoulClient(path = "/vehicle/stock/brand/list", desc = "获取品牌列表")
    @GetMapping("/brand/list")
    public Page<String> listBrand(@RequestParam("pageNum") int current,@RequestParam("pageSize")int size){
        return shopItemService.listBrand(current,size);
    }

    @SoulClient(path = "/vehicle/stock/item/list", desc = "获取商品列表")
    @GetMapping("/item/list")
    public PageInfo<ShopItemEntity> listItem(@RequestParam("pageNum") int current, @RequestParam("pageSize")int size){
        return shopItemService.listItem(current,size);
    }
}

