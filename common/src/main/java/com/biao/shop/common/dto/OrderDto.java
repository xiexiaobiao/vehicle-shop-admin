package com.biao.shop.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName OrderDTO
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/17
 * @Version V1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto implements Serializable {

    private static final long serialVersionUID=1L;
    private Integer idOrder;
    private String OrderUuid;
    private String clientUuid;
    private String clientName;
    private String vehiclePlate;
    private LocalDateTime generateDate;
    private LocalDateTime modifyDate;
    private Boolean paidStatus;
    private Boolean cancelStatus;
    private String orderRemark;
    private BigDecimal amount;
    // 订单明细
    List<OrderDto.ItemListDTO> detail;

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

    public Boolean getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(Boolean paidStatus) {
        this.paidStatus = paidStatus;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<ItemListDTO> getDetail() {
        return detail;
    }

    public void setDetail(List<ItemListDTO> detail) {
        this.detail = detail;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getOrderUuid() {
        return OrderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        OrderUuid = orderUuid;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public Boolean getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(Boolean cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    //    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
    public static class ItemListDTO {
        private String itemUuid;
        private Integer quantity;
        private BigDecimal discountPrice;
        private String remark;

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

        public BigDecimal getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(BigDecimal discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
