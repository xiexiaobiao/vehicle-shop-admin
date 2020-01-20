package com.biao.shop.common.mq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

public class RocketMQFactory {
    public DefaultMQProducer getDefaultProducer(String nameSrv,String groupName) throws MQClientException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(groupName);
        defaultMQProducer.setNamesrvAddr(nameSrv);
        defaultMQProducer.start();
        return defaultMQProducer;
    }

    public DefaultMQPushConsumer getMQPushConsumer(String nameSrv,String groupName) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(nameSrv);
        consumer.start();
        return  consumer;
    }

}
