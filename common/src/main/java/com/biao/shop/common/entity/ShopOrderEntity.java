package com.biao.shop.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.biao.shop.common.utils.CustomDateDeserializer;
import com.biao.shop.common.utils.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true) // 链式赋值
@TableName("shop_order")
public class ShopOrderEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id_order", type = IdType.AUTO)
    private Integer idOrder;

    /**
     * 订单流水号
     */
    private String orderUuid;

    // @TableField("client_uuid") 防止出错，可以自己指定映射字段
    private String clientUuid;

    private LocalDateTime generateDate;

    private LocalDateTime modifyDate;

    @TableField("is_paid")
    private Boolean paidStatus;
    @TableField("order_remark")
    private String orderRemark;

    @Override
    public String toString() {
        return "ShopOrderEntity{" +
                "idOrder=" + idOrder +
                ", OrderUuid='" + orderUuid + '\'' +
                ", clientUuid='" + clientUuid + '\'' +
                ", generateDate=" + generateDate +
                ", modifyDate=" + modifyDate +
                ", paidStatus=" + paidStatus +
                '}';
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }


    public String getClientUuid() {
        return clientUuid;
    }

    public void setClientUuid(String clientUuid) {
        this.clientUuid = clientUuid;
    }

    public LocalDateTime getGenerateDate() {
        return generateDate;
    }

    public void setGenerateDate(LocalDateTime generateDate) {
        this.generateDate = generateDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Boolean getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(Boolean paidStatus) {
        this.paidStatus = paidStatus;
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }
}
