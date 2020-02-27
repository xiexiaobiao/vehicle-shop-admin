package com.biao.shop.stock.service;

import com.biao.shop.common.dto.AliyunPolicy;
import com.biao.shop.common.response.ObjectResponse;

public interface AliyunOssService {
    ObjectResponse<AliyunPolicy> policy();
}
