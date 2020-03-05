package com.biao.shop.common.dto;

import com.biao.shop.common.entity.ShopItemEntity;

import java.math.BigDecimal;

/**
 * @ClassName ShopItemEntityDto
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/27
 * @Version V1.0
 **/
public class ShopItemEntityDto extends ShopItemEntity {
    private String picAddr;

    private Integer stock;

    private Integer sales;

    private BigDecimal discountPrice;

    public String getPicAddr() {
        return picAddr;
    }

    public void setPicAddr(String picAddr) {
        this.picAddr = picAddr;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }
}
