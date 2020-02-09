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
    private int status;
    private String message;

}
