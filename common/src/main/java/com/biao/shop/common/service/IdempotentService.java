package com.biao.shop.common.service;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName IdempotentService
 * @Description: TODO
 * @Author Biao
 * @Date 2020/4/11
 * @Version V1.0
 **/
public interface IdempotentService {
    /** 生成token */
    String generateToken() throws InterruptedException, ExecutionException, TimeoutException;
    /** token校验 */
    boolean checkToken(HttpServletRequest request);
}
