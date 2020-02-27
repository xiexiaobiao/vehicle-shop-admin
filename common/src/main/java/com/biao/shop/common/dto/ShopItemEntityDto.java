package com.biao.shop.common.dto;

import com.biao.shop.common.entity.ShopItemEntity;

/**
 * @ClassName ShopItemEntityDto
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/27
 * @Version V1.0
 **/
public class ShopItemEntityDto extends ShopItemEntity {
    private String picAddr;

    public String getPicAddr() {
        return picAddr;
    }

    public void setPicAddr(String picAddr) {
        this.picAddr = picAddr;
    }
}
