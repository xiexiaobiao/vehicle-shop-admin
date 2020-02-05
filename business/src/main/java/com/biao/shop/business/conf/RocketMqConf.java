package com.biao.shop.business.conf;

import com.biao.shop.business.mq.RocketMQConsumeMsgListener;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName RocketMqConf
 * @Description: TODO
 * @Author Biao
 * @Date 2020/1/31
 * @Version V1.0
 **/
public class RocketMqConf {
    @Autowired
    DefaultMQPushConsumer defaultMQPushConsumer;

    @Autowired
    RocketMQConsumeMsgListener rocketMQConsumeMsgListener;

    @Bean
    public DefaultMQPushConsumer defaultMQPushConsumer(){
        defaultMQPushConsumer.setConsumerGroup("group");
        defaultMQPushConsumer.registerMessageListener(rocketMQConsumeMsgListener);
        return  defaultMQPushConsumer;
    }
}
