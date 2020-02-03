package com.biao.shop.business.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biao.shop.common.bo.OrderBO;
import com.biao.shop.common.entity.ShopOrderEntity;
import com.biao.shop.business.mq.RocketMQTransProducer;
import com.biao.shop.common.service.ShopOrderService;
import com.biao.shop.common.utils.CustomDateSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-01-05
 */
@RestController
@RequestMapping("/shop-order-entity")
@Slf4j
public class ShopOrderController {
    private final Logger logger = LoggerFactory.getLogger(ShopOrderController.class);
    @Autowired
    private ShopOrderService orderService;
    @Autowired
    private RocketMQTransProducer rocketMQTransProducer;
    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public List<ShopOrderEntity> listOrder(){
        return orderService.queryOrder("10");
    }

    // 测试mysql数据保存功能
    @PostMapping(value = "/save") //@PostMapping等价于@RequestMapping(method = RequestMethod.POST)
    public int saveOrder(@RequestBody OrderBO  order){
        logger.debug("订单日期：{}", order.getGenerateDate());
        return orderService.saveOrder(order);
    }

    // 测试RocketMq事务消息功能
    @PostMapping(value = "/saveTrans")
    public TransactionSendResult saveTransOrder(@RequestBody OrderBO order) throws UnsupportedEncodingException, MQClientException, JsonProcessingException {
        logger.debug(order.toString());
        //jackson 序列化, 但要注意
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class, new CustomDateSerializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(timeModule);

        String JsonStr = objectMapper.writeValueAsString(order);
        Message msg = new Message("ShopTopic","order_tag",
                JsonStr.getBytes(RemotingHelper.DEFAULT_CHARSET));
        // 这里可以传入自定义的arg
        TransactionSendResult result = rocketMQTransProducer.sendMsg(msg,"customized-arg");
        return  result;
    }

    // 测试mybatis分页功能
    @GetMapping(value = "/page") //@PostMapping等价于@RequestMapping(method = RequestMethod.POST)
    public Page<ShopOrderEntity> queryOrderPage(){
        Page<ShopOrderEntity> result= orderService.queryOrderPagination(1,3);
        System.out.println(result.getRecords());
        return result;
    }

    //测试business向stock发送msg
    @GetMapping(value = "/msg")
    public SendResult msg() throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        LocalDateTime time = LocalDateTime.now(ZoneId.systemDefault());
        Message msg = new Message("ShopTopic","stock_tag",
                (time  + "-- RocketMQ message from business to stock test.").getBytes(RemotingHelper.DEFAULT_CHARSET));
        return defaultMQProducer.send(msg);
    }

//    @RequestMapping(value = "/pay",method = RequestMethod.GET)
//    public ResEntity<String> payOrder(@Param("orderId") String orderId){


}

