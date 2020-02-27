package com.biao.shop.stock.controller;

import com.biao.shop.common.dto.AliyunPolicy;
import com.biao.shop.common.response.ObjectResponse;
import com.biao.shop.stock.service.AliyunOssService;
import org.dromara.soul.client.common.annotation.SoulClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AliyunOSSController
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/26
 * @Version V1.0
 **/
@RestController
@RequestMapping("/stock/aliyun/oss")
public class AliyunOssController {
    private AliyunOssService aliyunOssService;

    @Autowired
    public AliyunOssController(AliyunOssService aliyunOssService) {
        this.aliyunOssService = aliyunOssService;
    }


    @SoulClient(path = "/vehicle/stock/aliyun/oss/policy", desc = "oss获取policy和签名信息")
    @GetMapping("/policy")
    public ObjectResponse<AliyunPolicy> policy(){
        return aliyunOssService.policy();
    }
}
