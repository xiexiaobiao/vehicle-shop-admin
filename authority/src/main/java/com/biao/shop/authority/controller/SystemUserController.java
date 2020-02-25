package com.biao.shop.authority.controller;

import com.biao.shop.authority.service.SystemUserService;
import com.biao.shop.common.dto.UserDto;
import com.biao.shop.common.response.ObjectResponse;
import lombok.extern.slf4j.Slf4j;
import org.dromara.soul.client.common.annotation.SoulClient;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserAdminController
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/9
 * @Version V1.0
 **/
@RestController
@Slf4j
@RequestMapping("/admin")
@MapperScan(basePackages = "com.biao.shop.common.dao")
public class SystemUserController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    SystemUserService systemUserService;

    private final Logger logger = LoggerFactory.getLogger(SystemUserController.class);

    //@ApiOperation(value = "登录以后返回token")
    @SoulClient(path = "/vehicle/admin/login", desc = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ObjectResponse login(@RequestBody UserDto userDto) {
        //String token = adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        logger.info("user info is : {} / {}",userDto.getUsername(),userDto.getPassword());
        String token = null;
        if (systemUserService.checkPasswd(userDto.getUsername(),userDto.getPassword())){
            logger.info("用户登录验证通过！");
            token = "123456789";
        };

        ObjectResponse objectResponse = new ObjectResponse();
        if (token == null) {
            objectResponse.setCode(500);
            objectResponse.setMessage("用户名或密码错误");
            return objectResponse;
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        objectResponse.setCode(200);
        objectResponse.setMessage( "操作成功");
        objectResponse.setData(tokenMap);
        return objectResponse;
    }

    // @ApiOperation(value = "获取当前登录用户信息")
    @SoulClient(path = "/vehicle/admin/info", desc = "获取用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ObjectResponse getAdminInfo() {
        ObjectResponse objectResponse = new ObjectResponse();
        objectResponse.setCode(200);
        objectResponse.setMessage( "操作成功");
        Map<String, Object> data = new HashMap<>();
        data.put("username", "admin");
        data.put("roles", new String[]{"TEST"});
        data.put("icon", "");
        objectResponse.setData(data);
        return objectResponse;
    }

    @SoulClient(path = "/vehicle/admin/logout", desc = "用户注销")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ObjectResponse logout() {
        ObjectResponse objectResponse = new ObjectResponse();
        objectResponse.setCode(200);
        objectResponse.setMessage( "操作成功");
        objectResponse.setData(null);
        return objectResponse;
    }
}
