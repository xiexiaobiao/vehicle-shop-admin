package com.biao.shop.common.dto;

import lombok.Data;

/**
 * @ClassName AliyunCallbackParam
 * @Description: 回调设置代码对象
 * @Author Biao
 * @Date 2020/2/26
 * @Version V1.0
 **/
@Data
public class AliyunCallbackParam {

    private String callbackUrl;
    private String callbackHost;
    private String callbackBody;
    private String callbackBodyType;

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getCallbackHost() {
        return callbackHost;
    }

    public void setCallbackHost(String callbackHost) {
        this.callbackHost = callbackHost;
    }

    public String getCallbackBody() {
        return callbackBody;
    }

    public void setCallbackBody(String callbackBody) {
        this.callbackBody = callbackBody;
    }

    public String getCallbackBodyType() {
        return callbackBodyType;
    }

    public void setCallbackBodyType(String callbackBodyType) {
        this.callbackBodyType = callbackBodyType;
    }
}
