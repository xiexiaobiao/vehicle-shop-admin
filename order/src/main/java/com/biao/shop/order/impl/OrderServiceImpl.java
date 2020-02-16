package com.biao.shop.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.shop.common.dao.ShopOrderDao;
import com.biao.shop.common.entity.ItemListEntity;
import com.biao.shop.common.entity.ShopOrderEntity;
import com.biao.shop.order.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

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

    @Autowired
    public OrderServiceImpl(ShopOrderDao shopOrderDao){
        this.shopOrderDao = shopOrderDao;
    }

    @Override
    public int deleteOrderByUuid(String uuid) {
        return shopOrderDao.delete(new LambdaQueryWrapper<ShopOrderEntity>().eq(ShopOrderEntity::getOrderUuid,uuid));
    }

    @Override
    public int deleteBatchByIds(Collection<Integer> ids) {
        return shopOrderDao.deleteBatchIds(ids);
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
    public List<ItemListEntity> getOrderList(String orderUid) {
        return null;
    }
}
