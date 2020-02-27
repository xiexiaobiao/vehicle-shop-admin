package com.biao.shop.common.dto;

import lombok.Data;

/**
 * @ClassName UserDto
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/9
 * @Version V1.0
 **/
@Data
public class UserDto {
    private String username;
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
