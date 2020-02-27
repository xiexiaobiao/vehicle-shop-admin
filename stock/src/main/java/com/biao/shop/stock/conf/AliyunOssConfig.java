package com.biao.shop.stock.conf;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.Credentials;
import com.aliyun.oss.common.auth.CredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName AliyunOssConfig
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/26
 * @Version V1.0
 **/
@SuppressWarnings("depre")
@Configuration
public class AliyunOssConfig {
        @Value("${aliyun.oss.endpoint}")
        private String ALIYUN_OSS_ENDPOINT;
        @Value("${aliyun.oss.accessKeyId}")
        private String ALIYUN_OSS_ACCESSKEYID;
        @Value("${aliyun.oss.accessKeySecret}")
        private String ALIYUN_OSS_ACCESSKEYSECRET;

        @Bean
        public OSSClient ossClient(){
            return new OSSClient(ALIYUN_OSS_ENDPOINT,ALIYUN_OSS_ACCESSKEYID,ALIYUN_OSS_ACCESSKEYSECRET);
        }

        // 替代的初始化OSSClient方案
/*        @Bean
        public OSSClient ossClient(){
            CredentialsProvider credsProvider = null;
            ClientConfiguration config = null;
            return new OSSClient(ALIYUN_OSS_ENDPOINT,credsProvider,config);
        }*/
}
