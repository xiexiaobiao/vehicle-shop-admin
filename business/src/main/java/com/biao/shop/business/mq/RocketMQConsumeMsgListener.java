package com.biao.shop.business.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Classname RocketMQConsumeMsgListener
 * @Description  实现消息的消费处理
 * @Author KOOL
 * @Date  2020/1/8 22:30
 * @Version 1.0
 **/
@Component
@Slf4j
public class RocketMQConsumeMsgListener implements MessageListenerConcurrently {

    private static final Logger logger = LoggerFactory.getLogger(RocketMQConsumeMsgListener.class);

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                    ConsumeConcurrentlyContext context) {
            if(CollectionUtils.isEmpty(msgs)){
                logger.info("received msg is empty!");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            //todo 消息消费处理业务
            /**消息可能会重复消费，务必注意这里的幂等处理，如使用order_id判断是否已经处理过*/
            msgs.forEach(msgExt -> {
                if (StringUtils.equals(msgExt.getTopic(),"ShopTopic")){
                        logger.info("tags is {} ",msgExt.getTags());
                        String body = new String(msgExt.getBody());
                        logger.info("msg body is {} ",body);
                        logger.info("msg ID is {}",msgExt.getMsgId());
                    };
                });
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }
