package com.biao.shop.authority.controller;

import com.biao.shop.authority.service.SystemUserService;
import com.biao.shop.common.annotation.LogAspect;
import com.biao.shop.common.constant.Constant;
import com.biao.shop.common.controller.BaseController;
import com.biao.shop.common.dto.UserDto;
import com.biao.shop.common.enums.RespStatusEnum;
import com.biao.shop.common.response.ObjectResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class SystemUserController extends BaseController {

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
    @LogAspect
    public ObjectResponse<String> login(@RequestBody UserDto userDto) throws JsonProcessingException {

        // 临时需要在web层使用request或response的方式如下； 如频繁使用，可以使用继承一个BaseController
        /*ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();*/

        // 继承自BaseController
        /*String idempotentId = request.getHeader(Constant.IDEMPOTENT_TOKEN);*/

        //String token = adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        logger.info("user info is : {} / {}",userDto.getUsername(),userDto.getPassword());
        String token = null;
        if (systemUserService.checkPasswd(userDto.getUsername(),userDto.getPassword())){
            logger.info("用户登录验证通过！");
            token = "vehicleAdminToken";
        };

        ObjectResponse<String> objectResponse = new ObjectResponse();
        if (token == null) {
            objectResponse.setCode(500);
            objectResponse.setMessage("用户名或密码错误");
            return objectResponse;
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        ObjectMapper objectMapper = new ObjectMapper();
        String res = objectMapper.writeValueAsString(tokenMap);
        objectResponse.setCode(RespStatusEnum.SUCCESS.getCode());
        objectResponse.setMessage(RespStatusEnum.SUCCESS.getMessage());
        objectResponse.setData(res);
        return objectResponse;
    }

    // @ApiOperation(value = "获取当前登录用户信息")
    @SoulClient(path = "/vehicle/admin/info", desc = "获取用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @LogAspect
    public ObjectResponse<Map<String, Object>> getAdminInfo() throws JsonProcessingException {
        ObjectResponse<Map<String, Object>> objectResponse = new ObjectResponse();
        objectResponse.setCode(200);
        objectResponse.setMessage( "操作成功");
        Map<String, Object> data = new HashMap<>();
        data.put("username", "admin");
        data.put("roles", new String[]{"TEST"});
        data.put("icon", "");
        objectResponse.setCode(RespStatusEnum.SUCCESS.getCode());
        objectResponse.setMessage(RespStatusEnum.SUCCESS.getMessage());
//        ObjectMapper objectMapper = new ObjectMapper();
////        String res = objectMapper.writeValueAsString(data);
        objectResponse.setData(data);
        return objectResponse;
    }

    @SoulClient(path = "/vehicle/admin/logout", desc = "用户注销")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @LogAspect
    public ObjectResponse<String> logout() {
        ObjectResponse<String> objectResponse = new ObjectResponse();
        objectResponse.setCode(RespStatusEnum.SUCCESS.getCode());
        objectResponse.setMessage(RespStatusEnum.SUCCESS.getMessage());
        objectResponse.setData(null);
        return objectResponse;
    }

    public void print(){

    }
}
