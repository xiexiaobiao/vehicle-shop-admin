package com.biao.shop.stock.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.biao.shop.common.dto.AliyunCallbackParam;
import com.biao.shop.common.dto.AliyunPolicy;
import com.biao.shop.common.enums.RespStatusEnum;
import com.biao.shop.common.response.ObjectResponse;
import com.biao.shop.common.utils.JacksonUtil;
import com.biao.shop.stock.service.AliyunOssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @ClassName AliyunOssServiceImpl
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/26
 * @Version V1.0
 **/
@Service
public class AliyunOssServiceImpl implements AliyunOssService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AliyunOssServiceImpl.class);
    @Value("${aliyun.oss.policy.expire}")
    private int ALIYUN_OSS_EXPIRE;
    @Value("${aliyun.oss.maxSize}")
    private int ALIYUN_OSS_MAX_SIZE;
    @Value("${aliyun.oss.callback}")
    private String ALIYUN_OSS_CALLBACK;
    @Value("${aliyun.oss.bucketName}")
    private String ALIYUN_OSS_BUCKET_NAME;
    @Value("${aliyun.oss.endpoint}")
    private String ALIYUN_OSS_ENDPOINT;
    @Value("${aliyun.oss.dir.prefix}")
    private String ALIYUN_OSS_DIR_PREFIX;

    private OSSClient ossClient;

    @Autowired
    public AliyunOssServiceImpl(OSSClient ossClient) {
        this.ossClient = ossClient;
    }

    @Override
    public ObjectResponse<AliyunPolicy> policy() {

        ObjectResponse<AliyunPolicy> result = new ObjectResponse<>();
        // 存储目录，按天区分
        DateTimeFormatter dtf =   DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String dir = ALIYUN_OSS_DIR_PREFIX + LocalDateTime.now().toLocalDate().format(dtf);
        // 签名有效期
        long expireEndTime = System.currentTimeMillis() + ALIYUN_OSS_EXPIRE * 1000;
        Date expiration = new Date(expireEndTime);
        // 文件大小
        long maxSize = ALIYUN_OSS_MAX_SIZE * 1024 * 1024;
        // 回调
        AliyunCallbackParam callback = new AliyunCallbackParam();
        callback.setCallbackUrl(ALIYUN_OSS_CALLBACK);
        callback.setCallbackBody("filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
        callback.setCallbackBodyType("application/x-www-form-urlencoded");
        // 提交节点
        String action = "http://" + ALIYUN_OSS_BUCKET_NAME + "." + ALIYUN_OSS_ENDPOINT;
        try {
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
            String policy = BinaryUtil.toBase64String(binaryData);
            String signature = ossClient.calculatePostSignature(postPolicy);
            // 对"callback"属性进行base64编码编码
            String callbackData = BinaryUtil.toBase64String(
                    JacksonUtil.convertToJson(callback).getBytes(StandardCharsets.UTF_8));
            // 返回结果
            AliyunPolicy aliyunPolicy = new AliyunPolicy();
            aliyunPolicy.setAccessKeyId(ossClient.getCredentialsProvider().getCredentials().getAccessKeyId());
            aliyunPolicy.setPolicy(policy);
            aliyunPolicy.setSignature(signature);
            aliyunPolicy.setExpire(String.valueOf(expireEndTime));
            aliyunPolicy.setDir(dir);
            aliyunPolicy.setCallback(callbackData);
            aliyunPolicy.setHost(action);
            result.setCode(RespStatusEnum.SUCCESS.getCode());
            result.setMessage(RespStatusEnum.SUCCESS.getMessage());
            result.setData(aliyunPolicy);
        } catch (Exception e) {
            LOGGER.error("signature failed !!", e);
        }
        return result;
    }
}
