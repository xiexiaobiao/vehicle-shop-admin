package com.biao.shop.common.dto;

import lombok.Data;

/**
 * @ClassName AliyunPolicy
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/26
 * @Version V1.0
 **/
@Data
public class AliyunPolicy {

    private String AccessKeyId;
    private String host;
    private String policy;
    private String signature;
    private String expire;
    private String callback;
    private String dir;



    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getAccessKeyId() {
        return AccessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        AccessKeyId = accessKeyId;
    }
}
