package com.biao.shop.business.mq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RocketMQTransProducer {
    @Autowired
    TransactionMQProducer transactionMQProducer;
    @Autowired
    TransListenerImpl transactionListener;

    public TransactionSendResult sendMsg(Message msg, Object arg) throws MQClientException {
        transactionMQProducer.setTransactionListener(transactionListener);
        return transactionMQProducer.sendMessageInTransaction(msg,arg);
    }

}
