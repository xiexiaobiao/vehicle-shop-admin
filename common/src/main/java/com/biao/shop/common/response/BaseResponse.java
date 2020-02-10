package com.biao.shop.common.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName BaseResponse
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/7
 * @Version V1.0
 **/
@Data
public class BaseResponse implements Serializable {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
