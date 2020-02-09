package com.biao.shop.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.shop.business.service.ItemListService;
import com.biao.shop.business.service.ShopOrderService;
import com.biao.shop.common.bo.OrderBO;
import com.biao.shop.common.dao.ShopOrderDao;
import com.biao.shop.common.entity.ItemListEntity;
import com.biao.shop.common.entity.ShopOrderEntity;
import com.biao.shop.common.rpc.service.ShopClientRPCService;
import com.biao.shop.common.rpc.service.ShopItemRPCService;
import com.biao.shop.common.utils.SnowFlake;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-01-05
 */
@Service
// 此版本springboot会注入两个bean，可以去掉@Primary看输出,接口也被视为同一service
// \vehicle-shop-admin\common\build\classes\java\main\com\biao\shop\common\service\ShopOrderService.class
@Primary
public class ShopOrderServiceImpl extends ServiceImpl<ShopOrderDao, ShopOrderEntity> implements ShopOrderService {

    private final Logger logger = LoggerFactory.getLogger(ShopOrderServiceImpl.class);

    private ShopOrderDao shopOrderDao; //订单
    private ItemListService itemListService; // 订单明细

    @Reference(version = "1.0",group = "shop",interfaceClass = ShopClientRPCService.class)
    private ShopItemRPCService itemRPCService; // 商品

    @Reference(version = "1.0",group = "shop",interfaceClass = ShopClientRPCService.class)
    private ShopClientRPCService clientRPCService;

    @Autowired
    public ShopOrderServiceImpl(ShopOrderDao shopOrderDao,ItemListService itemListService){
        this.shopOrderDao = shopOrderDao;
        this.itemListService = itemListService;
    }

    /** */
    // 订单的明细单独保存，
    @Override
    @Transactional(propagation = Propagation.NESTED)
    public int saveOrderUnpaid(OrderBO order) {

        /**测试模拟本地事务出错,"sendStatus": "SEND_OK",
         * 但是"localTransactionState": "ROLLBACK_MESSAGE"，半消息不会发送到下游*/
         // int a = 1 / 0 ;

        ShopOrderEntity orderEntity = new ShopOrderEntity();
        orderEntity.setGenerateDate(LocalDateTime.now());
        BeanUtils.copyProperties(order,orderEntity);
        // 可以使用其他算法生成UUID，如雪花，redis等
        // String orderUuid = String.valueOf(UUID.randomUUID());
        Long orderUuid = SnowFlake.generateId();
        logger.info("雪花id：{}",orderUuid);
        orderEntity.setUuid(String.valueOf(orderUuid));
        logger.debug("orderEntity: {}",orderEntity.toString());
        List<ItemListEntity> itemListEntities = new ArrayList<>(16);
        /**
         * 这里有个问题可以思考下，以下这行放forEach外边，结果怎样？
         * ItemListEntity listEntity = new ItemListEntity();*/
        // 保存订单明细
        order.getDetail().forEach(itemBo-> {
            ItemListEntity listEntity = new ItemListEntity();
            BeanUtils.copyProperties(itemBo,listEntity);
            listEntity.setOrderUuid(String.valueOf(orderUuid));
            itemListEntities.add(listEntity);
        });
        itemListService.saveBatch(itemListEntities);
        // 测试事务的传播属性，propagation
        int j = 1/0;
        return shopOrderDao.insert(orderEntity);

        /**另一种程序结构：这里也可使用manager层的类
         * OrderManager.saveOrder(OrderBO order),
         * 区别在于manager层先组合了各dao，service直接调用，上面的模式则是
         * service间调用，并且可以使用service.saveBatch()批处理方法。
         * 而dao无法使用batch处理
         * */
    }

    // 使用 dubbo RPC调用customer模块的服务
    @Override
    @Transactional
    public int saveOrderPaid(OrderBO order) {
        ShopOrderEntity orderEntity = new ShopOrderEntity();
        orderEntity.setGenerateDate(LocalDateTime.now());
        BeanUtils.copyProperties(order,orderEntity);
        // 可以使用其他算法生成UUID，如雪花，redis等
        // String orderUuid = String.valueOf(UUID.randomUUID());
        Long orderUuid = SnowFlake.generateId();
        orderEntity.setUuid(String.valueOf(orderUuid));
        // 已付款设置
        orderEntity.setPaid(true);
        List<ItemListEntity> itemListEntities = new ArrayList<>(16);
        /**
         * 这里有个问题可以思考下，以下这行放forEach外边，结果怎样？
         * ItemListEntity listEntity = new ItemListEntity();*/
        // 保存订单明细
        order.getDetail().forEach(itemBo-> {
            ItemListEntity listEntity = new ItemListEntity();
            BeanUtils.copyProperties(itemBo,listEntity);
            listEntity.setOrderUuid(String.valueOf(orderUuid));
            itemListEntities.add(listEntity);
            // 加积分,积分换算即售价取整
            int pointToAdd = itemRPCService.queryById(itemBo.getItemUuid()).getSellPrice().intValue();
            clientRPCService.addPoint(order.getClientUuid(),pointToAdd);
        });

        itemListService.saveBatch(itemListEntities);
        return shopOrderDao.insert(orderEntity);

        /**另一种程序结构：这里也可使用manager层的类
         * OrderManager.saveOrder(OrderBO order),
         * 区别在于manager层先组合了各dao，service直接调用，上面的模式则是
         * service间调用，并且可以使用service.saveBatch()批处理方法。
         * 而dao无法使用batch处理
         * */
    }

    @Override
    public int paidOrder(String orderId) {
        ShopOrderEntity orderEntity = this.queryOrder(orderId);
        if (Objects.isNull(orderEntity))
            return 0;
        else
            orderEntity.setPaid(true);
        return shopOrderDao.updateById(orderEntity);
    }

    @Override
    public int deleteOrder(String orderId) {
        QueryWrapper<ShopOrderEntity> qw = new QueryWrapper<>();
        qw.eq(true,"id_order",orderId);
        return shopOrderDao.delete(qw);
    }

    @Override
    public int modifyOrder(ShopOrderEntity order) {
        return shopOrderDao.updateById(order);
    }

    @Override
    public List<ShopOrderEntity> listOrder(String condition) {
        QueryWrapper<ShopOrderEntity> qw = new QueryWrapper<>();
        qw.eq(true,"id_order",condition);
        return shopOrderDao.selectList(qw);
    }

    @Override
    public ShopOrderEntity queryOrder(String orderId) {
        QueryWrapper<ShopOrderEntity> qw = new QueryWrapper<>();
        qw.eq(true,"id_order",orderId);
        return shopOrderDao.selectOne(qw);
    }

    @Override
    public boolean checkOrderSaveStatus(String orderUUID) {
        // 查询订单数据库，看orderUUID是否已经存在，
        QueryWrapper<ShopOrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne(true,"uuid",orderUUID);
        if (Objects.isNull(shopOrderDao.selectOne(queryWrapper))){
            return false;
        }
        log.debug("simulate checkOrderSaveStatus !!!");
        return true;
    }

    @Override
    public Page<ShopOrderEntity> queryOrderPagination(Integer current, Integer size) {
        QueryWrapper<ShopOrderEntity> queryWrapper = new QueryWrapper<>();
        // 特别注意，这里的null要加引号，mbp处理方式会转换
        queryWrapper.ne(true,"uuid","null");
        Page<ShopOrderEntity> shopOrderEntityPage = new Page<>(current,size);
        Page<ShopOrderEntity> result = shopOrderDao.selectPage(shopOrderEntityPage,queryWrapper);
        // 断言测试
        assert result != null ;
        return result;
    }
}
