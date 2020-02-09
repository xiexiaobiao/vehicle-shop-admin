package com.biao.shop.common.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName ObjectResponse
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/7
 * @Version V1.0
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class ObjectResponse<T> extends BaseResponse implements Serializable {
    private T data;
}
