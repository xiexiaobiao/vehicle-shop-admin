package com.biao.shop.common.dto;

import com.biao.shop.common.entity.ItemListEntity;

/**
 * @ClassName ItemListEntityDto
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/27
 * @Version V1.0
 **/
public class ItemListEntityDto extends ItemListEntity {
    private String itemName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
