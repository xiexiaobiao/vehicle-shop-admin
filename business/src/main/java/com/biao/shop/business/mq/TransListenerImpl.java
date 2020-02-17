package com.biao.shop.business.mq;

import com.biao.shop.common.bo.OrderBO;
import com.biao.shop.business.service.ShopBusinessService;
import com.biao.shop.common.dto.OrderDTO;
import com.biao.shop.common.utils.CustomDateDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @Classname TransactionListenerImpl
 * @Description  todo
 * @Author KOOL
 * @Date  2020/1/8 11:38
 * @Version 1.0
 **/
@Component
public class TransListenerImpl implements TransactionListener {
    private final Logger logger = LoggerFactory.getLogger(TransListenerImpl.class);

    @Autowired
    private ShopBusinessService shopBusinessService;

    // LocalTransactionState 是一个枚举类
    // 事务半消息发送成功时调用而执行本地事务
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        try{
            // 执行本地transaction逻辑
            if (StringUtils.isNotEmpty(new String(msg.getBody()))){
                logger.debug("customized args are: {}",arg.toString());
                logger.debug("msgBody is : {}",new String(msg.getBody()));
                // Jackson 反序列化，注意CustomDateDeserializer的使用
                JavaTimeModule timeModule = new JavaTimeModule();
                timeModule.addDeserializer(LocalDateTime.class,new CustomDateDeserializer());
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(timeModule);
                OrderDTO orderDTO = objectMapper.readValue(msg.getBody(), OrderDTO.class);
                logger.debug("orderBo is : {}",orderDTO.toString());
                //本地transaction
                shopBusinessService.saveOrderPaid(orderDTO);
            }
            return LocalTransactionState.COMMIT_MESSAGE;
        }catch (Exception e){
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    // 上面方法执行后，半消息无回应时，broker会执行此方法去检查本地事务状态，然后决定是否将消息发往下游
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        // Jackson 反序列化，注意CustomDateDeserializer的使用
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(LocalDateTime.class,new CustomDateDeserializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(timeModule);
        OrderBO orderBo = null;
        try {
            orderBo = objectMapper.readValue(msg.getBody(), OrderBO.class);
        } catch (IOException e) {
            e.printStackTrace();
    }
        assert orderBo != null;
        String orderUuid  = orderBo.getOrderUuid();
        if (null == msg.getTransactionId()) {
            // 此返回值将回查一次结束
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        // 如果订单uuid存在，则认为本地事务执行成功，半消息将发送到下游
        if (shopBusinessService.checkOrderSaveStatus(orderUuid)){
            // 此返回值将回查一次结束
            return LocalTransactionState.COMMIT_MESSAGE;
        }
        else {
            // 此返回值将执行多次，直到达到最大回查次数，默认15次
            return LocalTransactionState.UNKNOW;
        }
    }
}
