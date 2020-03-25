package com.biao.shop.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName ShopItemAppDTO
 * @Description: TODO
 * @Author Biao
 * @Date 2020/3/6
 * @Version V1.0
 **/
@Data
@Accessors(chain = true)// 链式赋值
public class ShopOrderAppDTO {
    private String  orderUuid;
    private String clientUuid;
    private String clientName; // 改为订单无需查看物品时添加
    private String vehiclePlate; // 改为订单无需查看物品时添加
    private String phone; // 改为订单无需查看物品时添加
    private LocalDateTime generateDate;
    private BigDecimal amount;
    private Boolean paidStatus;
    List<ShopOrderAppDTO.Detail> detail;

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
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

    public List<Detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public void setClientUuid(String clientUuid) {
        this.clientUuid = clientUuid;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Data
    @Accessors(chain = true)// 链式赋值
    public static class Detail {
        private String itemUuid;
        private String itemName;
        private String picAddr;
        private Integer quantity;
        private BigDecimal sellPrice;
        private BigDecimal discountPrice;
        private String brandName;
        private String specification;

        public String getItemUuid() {
            return itemUuid;
        }

        public void setItemUuid(String itemUuid) {
            this.itemUuid = itemUuid;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
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

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getSpecification() {
            return specification;
        }

        public void setSpecification(String specification) {
            this.specification = specification;
        }

        public String getPicAddr() {
            return picAddr;
        }

        public void setPicAddr(String picAddr) {
            this.picAddr = picAddr;
        }

        public BigDecimal getSellPrice() {
            return sellPrice;
        }

        public void setSellPrice(BigDecimal sellPrice) {
            this.sellPrice = sellPrice;
        }
    }
}
