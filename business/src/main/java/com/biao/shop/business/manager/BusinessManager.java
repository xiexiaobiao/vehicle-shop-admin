package com.biao.shop.business.manager;

import com.biao.shop.common.bo.OrderBO;

/**
 * @Classname OrderManager
 * @Description
 * 1） 对第三方平台封装的层，预处理返回结果及转化异常信息；
 * 2） 对 Service 层通用能力的下沉，如缓存方案、中间件通用处理；
 * 3） 与 DAO 层交互，对多个 DAO 的组合复用。
 * @Author KOOL
 * @Date  2020/1/17 10:11
 * @Version 1.0
 **/
public interface BusinessManager {
    int saveOrder(OrderBO order);
}
