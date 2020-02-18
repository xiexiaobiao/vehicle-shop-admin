package com.biao.shop.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.shop.common.dao.ShopOrderDao;
import com.biao.shop.common.entity.ItemListEntity;
import com.biao.shop.common.entity.ShopOrderEntity;
import com.biao.shop.common.rpc.service.ShopClientRPCService;
import com.biao.shop.order.service.ItemListService;
import com.biao.shop.order.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName OrderServiceImpl
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/15
 * @Version V1.0
 **/
@Service
public class OrderServiceImpl extends ServiceImpl<ShopOrderDao, ShopOrderEntity>  implements OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private ShopOrderDao shopOrderDao;
    private ItemListService itemListService;

    @Reference(version = "1.0.0",group = "shop",interfaceClass = ShopClientRPCService.class)
    private ShopClientRPCService clientRPCService;

    @Autowired
    public OrderServiceImpl(ShopOrderDao shopOrderDao,ItemListService itemListService){
        this.shopOrderDao = shopOrderDao;
        this.itemListService =  itemListService;
    }

    @Override
    public int deleteOrderByUuid(String uuid) {
        return shopOrderDao.delete(new LambdaQueryWrapper<ShopOrderEntity>().eq(ShopOrderEntity::getOrderUuid,uuid));
    }

    @Override
    public int deleteBatchByIds(Collection<Integer> ids) {
        ids.forEach(this::deleteById);
        return 1;
    }

    @Override
    @Transactional
    public int deleteById(int id) {
        ShopOrderEntity shopOrderEntity = shopOrderDao.selectById(id);
        String orderUuid = shopOrderEntity.getOrderUuid();
        // 删除订单详细
        List<Integer> list1 = itemListService.listDetail(orderUuid).stream()
                .map(ItemListEntity::getIdList).collect(Collectors.toList());
        itemListService.deleteBatchItemList(list1);
        return shopOrderDao.deleteById(id);
    }

    @Override
    public ShopOrderEntity selectByUuId(String uuid) {
        return shopOrderDao.selectOne(new LambdaQueryWrapper<ShopOrderEntity>().eq(ShopOrderEntity::getOrderUuid,uuid));
    }

    @Override
    public int updateOrder(ShopOrderEntity orderEntity) {
        return shopOrderDao.updateById(orderEntity);
    }

    @Override
    public int createOrder(ShopOrderEntity orderEntity) {
        return shopOrderDao.insert(orderEntity);
    }

    @Override
    public ShopOrderEntity queryOrder(int id) {
        return shopOrderDao.selectById(id);
    }

    @Override
    public PageInfo<ShopOrderEntity> listOrder(Integer current, Integer size) {
        QueryWrapper<ShopOrderEntity> qw = new QueryWrapper<>();
        qw.isNotNull("id_order");
        PageHelper.startPage(current,size);
        List<ShopOrderEntity> orderEntities = shopOrderDao.selectList(qw);
        return PageInfo.of(orderEntities);
    }

    @Override
    public int paidOrder(int orderId) {
        ShopOrderEntity orderEntity = this.queryOrder(orderId);
        if (Objects.isNull(orderEntity))
            return 0;
        else{
            orderEntity.setPaidStatus(true);
            // 加积分
            clientRPCService.addPoint(orderEntity.getClientUuid(),orderEntity.getAmount().intValue());
        }
        return shopOrderDao.updateById(orderEntity);
    }

}
