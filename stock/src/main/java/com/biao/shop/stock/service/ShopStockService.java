package com.biao.shop.stock.service;

import com.biao.shop.common.entity.ShopStockEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.rocketmq.client.exception.MQClientException;

import java.util.List;

/**
 * <p>
 * 库存表 服务类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-01-06
 */
public interface ShopStockService extends IService<ShopStockEntity> {
    int saveStock(ShopStockEntity  Stock) throws MQClientException;
    int deleteStock(String uuid);
    int modifyStock(ShopStockEntity  Stock);
    List<ShopStockEntity > queryOrder(String condition);

    int unfrozenStock(String itemUuid, int frozenQuantity) throws Exception;
    int frozenStock(String itemUuid, int frozenQuantity) throws Exception;
    int decreaseStock(String itemUuid, int decreaseQuantity) throws Exception;

    int increaseSale(String itemUuid, int saleQuantity);
}
