package com.biao.shop.stock.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biao.shop.common.dto.ShopItemEntityDto;
import com.biao.shop.common.entity.ShopItemPictureEntity;
import com.biao.shop.common.enums.RespStatusEnum;
import com.biao.shop.common.response.ObjectResponse;
import com.biao.shop.stock.service.ShopItemPictureService;
import org.dromara.soul.client.common.annotation.SoulClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品图片 前端控制器
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-02-27
 */
@RestController
@RequestMapping("/stock")
@MapperScan(basePackages = "com.biao.shop.common.dao")
public class ShopItemPictureController {

    private ShopItemPictureService itemPictureService;

    @Autowired
    public ShopItemPictureController(ShopItemPictureService itemPictureService) {
        this.itemPictureService = itemPictureService;
    }

    @SoulClient(path = "/vehicle/stock/item/pictures", desc = "获取商品最大编号值")
    @GetMapping("/item/pictures")
    public ObjectResponse<List<String>> maxItemId(@RequestParam(value = "itemUuid") String itemUuid){
        List<String> list = itemPictureService.listItemPictures(itemUuid)
                .stream().map(ShopItemPictureEntity::getPicAddr).collect(Collectors.toList());
        ObjectResponse<List<String>> response = new ObjectResponse<>();
        response.setCode(RespStatusEnum.SUCCESS.getCode());
        response.setMessage(RespStatusEnum.SUCCESS.getMessage());
        response.setData(list);
        return response;
    }
}

