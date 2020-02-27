package com.biao.shop.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biao.shop.common.dto.ItemListEntityDto;
import com.biao.shop.common.entity.ItemListEntity;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 订单商品明细清单 服务类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-01-06
 */
public interface ItemListService extends IService<ItemListEntity> {
    boolean saveDetail(List<ItemListEntity> list);
    int deleteBatchItemList(Collection<Integer> ids);
    int modifyDetail();
    List<ItemListEntity> listDetail(String orderUid);
    int deleteByOrderUid(String orderUid);
    List<ItemListEntityDto> listDetailName(String orderUid);
}
