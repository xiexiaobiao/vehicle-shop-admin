package com.biao.shop.business.mq;

import com.biao.shop.common.bo.OrderBO;
import com.biao.shop.common.service.ShopOrderService;
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
    private ShopOrderService shopOrderService;

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
                OrderBO orderBo = objectMapper.readValue(msg.getBody(), OrderBO.class);
                logger.debug("orderBo is : {}",orderBo.toString());
                //本地transaction
                shopOrderService.saveOrder(orderBo);
            }
            return LocalTransactionState.COMMIT_MESSAGE;
        }catch (Exception e){
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    // 上面方法执行后，半消息无回应时，broker会执行此方法去检查本地事务状态
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        if (null == msg.getTransactionId()) {
            // 回查一次结束
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        if (shopOrderService.checkOrderSaveStatus("UUID")){
            // 回查一次结束
            return LocalTransactionState.COMMIT_MESSAGE;
        }
        else {
            // 执行多次，知道达到最大回查次数，默认15次
            return LocalTransactionState.UNKNOW;
        }
    }
}
