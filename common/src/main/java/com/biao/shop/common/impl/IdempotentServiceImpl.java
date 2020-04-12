package com.biao.shop.common.impl;

import com.biao.shop.common.constant.Constant;
import com.biao.shop.common.service.IdempotentService;
import com.biao.shop.common.utils.RedisLettuceUtil;
import io.lettuce.core.RedisFuture;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * @ClassName IdempotentServiceImpl
 * @Description: TODO
 * @Author Biao
 * @Date 2020/4/11
 * @Version V1.0
 **/
public class IdempotentServiceImpl implements IdempotentService {


    @Override
    public String generateToken() throws InterruptedException, ExecutionException, TimeoutException {
        String key = String.valueOf(UUID.randomUUID());
        // 500 milliseconds后key过期
        RedisFuture<String> redisFuture = RedisLettuceUtil.asyncSet(key, Constant.IDEMPOTENT_TOKEN);
        if (StringUtils.isNotBlank(redisFuture.get(1000L, TimeUnit.MILLISECONDS))){
            return key;
        }
        return null;
    }

    @Override
    public boolean checkToken(HttpServletRequest request) {
        String token = request.getHeader(Constant.IDEMPOTENT_TOKEN);
        if (StringUtils.isBlank(token)){
            token = request.getParameter(Constant.IDEMPOTENT_TOKEN);
            if (StringUtils.isBlank(token)){
                throw new RuntimeException("Repeated request");
            }
        }
        return RedisLettuceUtil.delete(token);
    }
}
