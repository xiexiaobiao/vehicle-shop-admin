package com.biao.shop.common.enums;

/**
 * @ClassName RespStatusEnum
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/7
 * @Version V1.0
 **/
public enum RespStatusEnum {
  SUCCESS(200,"SUCCESS"),
  FAIL(500,"SYSTEM FAIL"),
  UNKNOWN(900,"UNKNOWN");

    private int code;
    private String message;

    RespStatusEnum(int i, String str) {
        this.code = i;
        this.message = str;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
