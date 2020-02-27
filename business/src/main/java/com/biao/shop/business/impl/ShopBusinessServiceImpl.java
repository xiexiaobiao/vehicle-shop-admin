package com.biao.shop.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.shop.business.service.ShopBusinessService;
import com.biao.shop.common.bo.OrderBo;
import com.biao.shop.common.dao.ShopOrderDao;
import com.biao.shop.common.dto.OrderDto;
import com.biao.shop.common.entity.ItemListEntity;
import com.biao.shop.common.entity.ShopClientEntity;
import com.biao.shop.common.entity.ShopItemEntity;
import com.biao.shop.common.entity.ShopOrderEntity;
import com.biao.shop.common.rpc.service.ShopClientRPCService;
import com.biao.shop.common.rpc.service.ShopOrderRPCService;
import com.biao.shop.common.rpc.service.ShopStockRPCService;
import com.biao.shop.common.utils.ConvertUpMoney;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

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
public class ShopBusinessServiceImpl extends ServiceImpl<ShopOrderDao, ShopOrderEntity> implements ShopBusinessService {

    private final Logger logger = LoggerFactory.getLogger(ShopBusinessServiceImpl.class);

    private ShopOrderDao shopOrderDao;

    @Reference(version = "1.0.0",group = "shop",interfaceClass = ShopOrderRPCService.class)
    private ShopOrderRPCService orderRPCService; // 订单

    @Reference(version = "1.0.0",group = "shop",interfaceClass = ShopStockRPCService.class)
    private ShopStockRPCService stockRPCService; // 商品 + 库存

    @Reference(version = "1.0.0",group = "shop",interfaceClass = ShopClientRPCService.class)
    private ShopClientRPCService clientRPCService;

    @Autowired
    public ShopBusinessServiceImpl(ShopOrderDao shopOrderDao){
        this.shopOrderDao = shopOrderDao;
    }

    /** */
    // 保存新订单
    @Override
    @Transactional(propagation = Propagation.NESTED)
    public int saveOrderDTO(OrderDto orderDTO) {

        /**测试模拟本地事务出错,"sendStatus": "SEND_OK",
         * 但是"localTransactionState": "ROLLBACK_MESSAGE"，半消息不会发送到下游*/
         // int a = 1 / 0 ;

        ShopOrderEntity orderEntity = new ShopOrderEntity();
        BeanUtils.copyProperties(orderDTO,orderEntity);
        orderEntity.setGenerateDate(LocalDateTime.now());
        // 可以使用其他算法生成UUID，如雪花，redis等
        // String orderUuid = String.valueOf(UUID.randomUUID());
        Long orderUuid = SnowFlake.generateId();
        logger.info("雪花id：{}",orderUuid);
        orderEntity.setOrderUuid(String.valueOf(orderUuid));
        logger.debug("orderEntity: {}",orderEntity.toString());
        List<ItemListEntity> itemListEntities = new ArrayList<>(16);
        /**
         * 这里有个问题可以思考下，以下这行放forEach外边，结果怎样？
         * ItemListEntity listEntity = new ItemListEntity();*/
        AtomicReference<Double> orderAmount = new AtomicReference<>(0.00);
        // 保存订单明细
        orderDTO.getDetail().forEach(itemDTO-> {
            ItemListEntity listEntity = new ItemListEntity();
            BeanUtils.copyProperties(itemDTO,listEntity);
            listEntity.setOrderUuid(String.valueOf(orderUuid));
            itemListEntities.add(listEntity);
            orderAmount.updateAndGet(v -> v + itemDTO.getDiscountPrice().doubleValue() * itemDTO.getQuantity());
            if (orderEntity.getPaidStatus()){
                // 扣减库存
                try {
                    stockRPCService.decreaseStock(itemDTO.getItemUuid(),itemDTO.getQuantity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else { // 冻结库存，未付款的
                try {
                    stockRPCService.frozenStock(itemDTO.getItemUuid(),itemDTO.getQuantity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        orderRPCService.saveBatchItems(itemListEntities);
        orderEntity.setAmount(BigDecimal.valueOf(orderAmount.get()));
        orderEntity.setCapitalAmount(ConvertUpMoney.toChinese(String.valueOf(orderEntity.getAmount())));
        // 已付款的加积分 订单金额直接等价于积分数
        if (orderEntity.getPaidStatus()){
            clientRPCService.addPoint(orderEntity.getClientUuid(),orderAmount.get().intValue());
        }
        /** 测试事务的传播属性，propagation */
        // int j = 1/0;

        return shopOrderDao.insert(orderEntity);

        /**另一种程序结构：这里也可使用manager层的类
         * OrderManager.saveOrder(OrderBO order),
         * 区别在于manager层先组合了各dao，service直接调用，上面的模式则是
         * service间调用，并且可以使用service.saveBatch()批处理方法。
         * 而dao无法使用batch处理
         * */
    }

    @Override
    @Transactional
    public int updateOrderDTO(OrderDto orderDTO) {
        String orderUuid = orderDTO.getOrderUuid();
        ShopOrderEntity orderEntity = orderRPCService.selectByUuId(orderUuid);
        BeanUtils.copyProperties(orderDTO,orderEntity);
        orderEntity.setModifyDate(LocalDateTime.now());
        List<ItemListEntity> itemListEntities = new ArrayList<>(16);
        AtomicReference<Double> orderAmount = new AtomicReference<>(0.00);
        // 先删除订单明细
        orderRPCService.deleteItemListByOrderUid(orderUuid);
        // 再保存订单明细
        orderDTO.getDetail().forEach(itemDTO-> {
            ItemListEntity listEntity = new ItemListEntity();
            BeanUtils.copyProperties(itemDTO,listEntity);
            listEntity.setOrderUuid(orderUuid);
            itemListEntities.add(listEntity);
            orderAmount.updateAndGet(v -> v + itemDTO.getDiscountPrice().doubleValue() * itemDTO.getQuantity());
            if (orderEntity.getPaidStatus()){
                clientRPCService.addPoint(orderEntity.getClientUuid(),orderAmount.get().intValue());
                // 取消冻结库存
                try {
                    stockRPCService.unfrozenStock(itemDTO.getItemUuid(),itemDTO.getQuantity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 扣减库存
                try {
                    stockRPCService.decreaseStock(itemDTO.getItemUuid(),itemDTO.getQuantity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        orderRPCService.saveBatchItems(itemListEntities);
        // 更新订单总金额
        orderEntity.setAmount(BigDecimal.valueOf(orderAmount.get()));
        orderEntity.setCapitalAmount(ConvertUpMoney.toChinese(String.valueOf(orderEntity.getAmount())));
        // 已付款的加积分 订单金额直接等价于积分数，
        if (orderEntity.getPaidStatus()){
            clientRPCService.addPoint(orderEntity.getClientUuid(),orderAmount.get().intValue());
        }
        // 最后更新订单主表
        return shopOrderDao.update(orderEntity,
                new LambdaQueryWrapper<ShopOrderEntity>().eq(ShopOrderEntity::getOrderUuid,orderUuid));
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

    // 查询一个订单详细
    @Override
    public OrderBo getOrderBO(int idOrder) {
        OrderBo orderBO = new OrderBo();
        ShopOrderEntity orderEntity = orderRPCService.queryOrder(idOrder);
        BeanUtils.copyProperties(orderEntity,orderBO);
        List<ItemListEntity> itemListEntityList = orderRPCService.getOrderItemList(orderEntity.getOrderUuid());
        List<OrderBo.ItemListBO> itemListBOList = new ArrayList<>(16);
        itemListEntityList.stream().forEach(
                itemListEntity -> {
                    OrderBo.ItemListBO itemListBO = orderBO.new ItemListBO();
                    BeanUtils.copyProperties(itemListEntity,itemListBO);
                    ShopItemEntity item = stockRPCService.queryByUuId(itemListEntity.getItemUuid());
                    if ( !Objects.isNull(item)){
                        BeanUtils.copyProperties(item,itemListBO);
                    }
                    itemListBOList.add(itemListBO);
                } );
        orderBO.setDetail(itemListBOList);
        ShopClientEntity client = clientRPCService.queryById(orderEntity.getClientUuid());
        BeanUtils.copyProperties(client,orderBO);
        orderRPCService.getOrderItemList(orderEntity.getOrderUuid());
        return orderBO;
    }
}
