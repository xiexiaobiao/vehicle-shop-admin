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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @SoulClient(path = "/vehicle/stock/item/del", desc = "删除一个商品")
    // 由于soul网关使用中发现无法支持DELETE转发，只能改为使用GET
    // @DeleteMapping("/item/del")
    @GetMapping("/item/del")
    public int deleteItem(@RequestParam("ids") String ids){
        if (ids.contains(",")){
            List<Integer> list = new ArrayList<>(8);
            String[] strings = ids.split(",");
            for (int i = 0; i < strings.length; i++) {
                list.add(Integer.valueOf(strings[i]));
            }
            return shopItemService.deleteBatchItems(list);
        }
        return shopItemService.deleteById(Integer.valueOf(ids));
    }

    @SoulClient(path = "/vehicle/stock/item/update", desc = "更新一个商品")
    @PutMapping("/item/update")
    public int updateItem(@RequestBody String jsonStr) {
        System.out.println(jsonStr);
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        ShopItemEntity itemEntity = new ShopItemEntity();
        itemEntity.setIdItem((Integer) jsonObject.get("idItem"));
        String brandName = (String) jsonObject.get("brandName");
        itemEntity.setBrandName(StringUtils.isEmpty(brandName)? null : brandName);
        itemEntity.setCategory((String) jsonObject.get("category"));//
        itemEntity.setItemName((String) jsonObject.get("itemName"));//
        String purPrice = (String) jsonObject.get("purchasePrice");
        itemEntity.setPurchasePrice( StringUtils.isEmpty(purPrice)? new BigDecimal("0.00") : new BigDecimal(purPrice));
        String price = (String) jsonObject.get("sellPrice");
        itemEntity.setSellPrice( StringUtils.isEmpty(price)? new BigDecimal("0.00") : new BigDecimal(price));
        String specification = (String) jsonObject.get("specification");
        itemEntity.setSpecification(StringUtils.isEmpty(specification)? null : specification);
        itemEntity.setItemUuid((String) jsonObject.get("itemUuid"));//
        String description = (String) jsonObject.get("description");
        itemEntity.setDescription(StringUtils.isEmpty(description)? null : description);
        Boolean shipment = (Boolean) jsonObject.get("shipment");
        itemEntity.setShipment(shipment);
        return shopItemService.updateItem(itemEntity);
    }

    @SoulClient(path = "/vehicle/stock/item/**", desc = "查询一个商品")
    @GetMapping("/item/{id}")
    public ShopItemEntity queryById(@PathVariable("id") String id) {
        ShopItemEntity itemEntity =  shopItemService.queryById(id);
        System.out.println(itemEntity);
        return itemEntity;
    }
    /*@PathVariable其他格式的：*/
    //@GetMapping("/path/{id}/name")
    //@SoulClient(path = "/test/path/**/name", desc = "test restful风格支持")

    @SoulClient(path = "/vehicle/stock/item/save", desc = "新增商品")
    @PostMapping("/item/save")
    public int addItem(@RequestBody String jsonStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        ShopItemEntity itemEntity = new ShopItemEntity();
        String brand = (String) jsonObject.get("brandName");
        itemEntity.setBrandName(StringUtils.isEmpty(brand)? null : brand);
        itemEntity.setCategory((String) jsonObject.get("category"));//
        itemEntity.setItemName((String) jsonObject.get("itemName"));//
        String purPrice = (String) jsonObject.get("purchasePrice");
        itemEntity.setPurchasePrice( StringUtils.isEmpty(purPrice)? new BigDecimal("0.00") : new BigDecimal(purPrice));
        String price = (String) jsonObject.get("sellPrice");
        itemEntity.setSellPrice( StringUtils.isEmpty(price)? new BigDecimal("0.00") : new BigDecimal(price));
        String specification = (String) jsonObject.get("specification");
        itemEntity.setSpecification(StringUtils.isEmpty(specification)? null : specification);
        itemEntity.setItemUuid((String) jsonObject.get("itemUuid"));//
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

    @SoulClient(path = "/vehicle/stock/category/list", desc = "获取类别列表")
    @GetMapping("/category/list")
    public List<String> listCategory(@RequestParam("pageNum") int current,@RequestParam("pageSize")int size){
        return shopItemService.listCategory(current,size);
    }

    @SoulClient(path = "/vehicle/stock/item/list", desc = "获取商品列表")
    @GetMapping("/item/list")
    public PageInfo<ShopItemEntity> listItem(@RequestParam("pageNum") int current, @RequestParam("pageSize")int size,
                                             @RequestParam(value = "itemName",required = false) String itemName,
                                             @RequestParam(value = "itemUuid",required = false)String itemUuid,
                                             @RequestParam(value = "category",required = false) String category,
                                             @RequestParam(value = "brandName",required = false)String brandName,
                                             @RequestParam(value = "shipment",required = false) int shipment){
        return shopItemService.listItem(current,size,itemName,itemUuid,category,brandName,shipment);
    }
}

