package com.biao.shop.common.bo;

import com.biao.shop.common.utils.CustomDateDeserializer;
import com.biao.shop.common.utils.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.List;

public class OrderBO {
    /**
     * 订单流水号
     */
    private String uuid;

    // @TableField("") 如为防止出错，可以自己指定映射字段
    private String clientUuid;

    private Integer idList;

    /** @JsonSerialize(using = CustomDateSerializer.class)
     @JsonDeserialize(using = CustomDateDeserializer.class)
     如果这里直接指定，会导致 所有使用OrderBO*/
    private LocalDateTime generateDate;

/** @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    如果这里直接指定，会导致 所有使用OrderBO类的对象都使用这个序列化，controller就无法解析接收的参数了，直接报错
    可以在具体使用ObjectMapper的时候直接使用自定义的序列化类*/
    private LocalDateTime modifyDate;

    // 订单明细
    List<itemBo> detail;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public void setClientUuid(String clientUuid) {
        this.clientUuid = clientUuid;
    }

    public Integer getIdList() {
        return idList;
    }

    public void setIdList(Integer idList) {
        this.idList = idList;
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

    public List<itemBo> getDetail() {
        return detail;
    }

    public void setDetail(List<itemBo> detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "OrderBO{" +
                "uuid='" + uuid + '\'' +
                ", clientUuid='" + clientUuid + '\'' +
                ", idList=" + idList +
                ", generateDate=" + generateDate +
                ", modifyDate=" + modifyDate +
                ", detail=" + detail.toString() +
                '}';
    }

    public static class itemBo{
        private String itemUuid;
        private Integer quantity;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        @Override
        public String toString() {
            return "itemBo{" +
                    "itemUuid='" + itemUuid + '\'' +
                    ", quantity=" + quantity +
                    ", remark='" + remark + '\'' +
                    '}';
        }
    }
}
