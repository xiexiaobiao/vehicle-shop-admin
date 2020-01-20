package com.biao.shop.business.manager;

import com.biao.shop.common.bo.OrderBO;
import com.biao.shop.common.dao.ItemListDao;
import com.biao.shop.common.dao.ShopOrderDao;
import com.biao.shop.common.entity.ItemListEntity;
import com.biao.shop.common.entity.ShopOrderEntity;
import com.biao.shop.common.utils.SnowFlake;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
@Component
public class OrderManager {
    @Autowired
    ItemListDao itemListDao;
    @Autowired
    ShopOrderDao shopOrderDao;

    public int saveOrder(OrderBO order) {
        ShopOrderEntity orderEntity = new ShopOrderEntity();
        BeanUtils.copyProperties(order,orderEntity);
        // 可以使用其他算法生成UUID，如雪花，redis等
        // String orderUuid = String.valueOf(UUID.randomUUID());
        Long orderUuid = SnowFlake.generateId();
        orderEntity.setUuid(String.valueOf(orderUuid));
        ItemListEntity listEntity = new ItemListEntity();
        order.getDetail().forEach(itemBo-> {
            BeanUtils.copyProperties(itemBo,listEntity);
            listEntity.setOrderUuid(String.valueOf(orderUuid));
            itemListDao.insert(listEntity);
        });
        return shopOrderDao.insert(orderEntity);
    }
}
