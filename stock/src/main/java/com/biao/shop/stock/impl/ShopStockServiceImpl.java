package com.biao.shop.stock.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.shop.common.dao.ShopStockDao;
import com.biao.shop.common.entity.ShopStockEntity;
import com.biao.shop.common.service.ShopStockService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 库存表 服务实现类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-01-06
 */
@Service
public class ShopStockServiceImpl extends ServiceImpl<ShopStockDao, ShopStockEntity> implements ShopStockService {

    private Logger logger = LoggerFactory.getLogger(ShopStockServiceImpl.class);

    @Autowired
    ShopStockDao stockDao;

    @Override
    public int saveStock(ShopStockEntity Stock) {
        System.out.println("saveStock method called >>>");
        return 0;
    }

    @Override
    public int deleteStock(String uuid) {
        return 0;
    }

    @Override
    public int modifyStock(ShopStockEntity Stock) {
        return 0;
    }

    @Override
    public List<ShopStockEntity> queryOrder(String condition) {
        return null;
    }

    @Override
    public int decrStock(String itemUuid, int lockQuantity) throws Exception {
        QueryWrapper<ShopStockEntity> qw = new QueryWrapper<>();
        qw.eq(true,"item_uuid",itemUuid);
        ShopStockEntity shopStockEntity = stockDao.selectOne(qw);
        if (shopStockEntity.getQuantityLocked() + lockQuantity > shopStockEntity.getQuantity()){
            throw new Exception("stock has not enough stock quantity!!!!");
        }
        shopStockEntity.setQuantityLocked(shopStockEntity.getQuantityLocked() + lockQuantity);
        return stockDao.updateById(shopStockEntity);
    }
}
