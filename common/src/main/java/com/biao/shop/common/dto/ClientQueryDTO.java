package com.biao.shop.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName ClientDTO
 * @Description: 订单查询时，先查询客户的条件构造对象
 * @Author Biao
 * @Date 2020/2/19
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
public class ClientQueryDTO implements Serializable {

    private String clientName;

    private String phone;

    private String vehiclePlate;

    private String vehicleSeries;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public String getVehicleSeries() {
        return vehicleSeries;
    }

    public void setVehicleSeries(String vehicleSeries) {
        this.vehicleSeries = vehicleSeries;
    }
}
