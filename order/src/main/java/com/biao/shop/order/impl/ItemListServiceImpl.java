package com.biao.shop.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.biao.shop.common.dto.ItemListEntityDto;
import com.biao.shop.common.entity.ItemListEntity;
import com.biao.shop.common.dao.ItemListDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.shop.common.rpc.service.ShopStockRPCService;
import com.biao.shop.order.service.ItemListService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单商品明细清单 服务实现类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-01-06
 */
@Service
public class ItemListServiceImpl extends ServiceImpl<ItemListDao, ItemListEntity> implements ItemListService {

    @Autowired
    ItemListDao itemListDao;

    @Reference(version = "1.0.0",group = "shop")
    private ShopStockRPCService stockRPCService;

    @Override
    public boolean saveDetail(List<ItemListEntity> list) {
        list.forEach(itemListEntity -> itemListDao.insert(itemListEntity));
        return true;
    }

    @Override
    public int deleteBatchItemList(Collection<Integer> ids) {
        return itemListDao.deleteBatchIds(ids);
    }

    @Override
    public int modifyDetail() {
        return 0;
    }

    @Override
    public List<ItemListEntity> listDetail(String orderUuid) {
        return itemListDao.selectList(
                new LambdaQueryWrapper<ItemListEntity>().eq(ItemListEntity::getOrderUuid,orderUuid));
    }

    @Override
    public int deleteByOrderUid(String orderUuid) {
        return itemListDao.delete(new LambdaQueryWrapper<ItemListEntity>().eq(ItemListEntity::getOrderUuid,orderUuid));
    }

    @Override
    public List<ItemListEntityDto> listDetailName(String orderUid) {
        return this.listDetail(orderUid).parallelStream().map(itemListEntity -> {
            ItemListEntityDto itemListEntityDto = new ItemListEntityDto();
            BeanUtils.copyProperties(itemListEntity,itemListEntityDto);
            itemListEntityDto.setItemName(stockRPCService.queryByUuId(itemListEntity.getItemUuid()).getItemName());
            return itemListEntityDto;
        }).collect(Collectors.toList());
    }
}
