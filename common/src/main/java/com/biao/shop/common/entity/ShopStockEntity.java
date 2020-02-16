package com.biao.shop.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 库存表
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true) // 链式赋值
@TableName("shop_stock")
public class ShopStockEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id_stock", type = IdType.AUTO)
    private Integer idStock;

    private String itemUuid;

    private Integer quantity;

    private Integer quantityLocked;


    public Integer getIdStock() {
        return idStock;
    }

    public void setIdStock(Integer idStock) {
        this.idStock = idStock;
    }

    public String getItemUuid() {
        return itemUuid;
    }

    public void setItemUuid(String itemUuid) {
        this.itemUuid = itemUuid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantityLocked() {
        return quantityLocked;
    }

    public void setQuantityLocked(Integer quantityLocked) {
        this.quantityLocked = quantityLocked;
    }
}
