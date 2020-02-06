package com.biao.shop.stock.conf;

import com.biao.shop.common.bo.OrderBO;
import com.biao.shop.common.entity.ShopStockEntity;
import com.biao.shop.common.service.ShopStockService;
import com.biao.shop.common.utils.CustomDateDeserializer;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.TopicMessageQueueChangeListener;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Configuration
public class RocketMQConsumer {

    private ShopStockService shopStockService;
    private DefaultMQPushConsumer defaultMQPushConsumer;

    @Autowired
    public RocketMQConsumer(ShopStockService shopStockService,DefaultMQPushConsumer defaultMQPushConsumer){
        this.shopStockService = shopStockService;
        this.defaultMQPushConsumer = defaultMQPushConsumer;
    }

    private final Logger logger = LoggerFactory.getLogger(RocketMQConsumer.class);

    @Bean(name = "StockDefaultMQPushConsumer")
    @Scope(value = "prototype",proxyMode = ScopedProxyMode.TARGET_CLASS)
    public DefaultMQPushConsumer defaultMQPushConsumer() throws MQClientException {
        defaultMQPushConsumer.setConsumerGroup("shop");
        defaultMQPushConsumer.subscribe("ShopTopic","order_tag");
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        /** RocketMQ supports two message models: clustering and broadcasting. If clustering is set, consumer clients with
         * the same {@link #consumerGroup} would only consume shards of the messages subscribed, which achieves load
         * balances; Conversely, if the broadcasting is set, each consumer client will consume all subscribed messages
         * separately.*/
        defaultMQPushConsumer.setMessageModel(MessageModel.BROADCASTING);
        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                if (CollectionUtils.isEmpty(msgs)){
                    logger.info("Rocket MQ is empty");
                }
                //todo 消息消费处理业务
                /**消息可能会重复消费，务必注意这里的幂等处理，比如使用order_id判断是否已经处理过*/
                msgs.forEach(msgExt -> {
                        logger.info("tags is {} ",msgExt.getTags());
                        String msg = new String(msgExt.getBody());
                        logger.info("msg body is {} ",msg);
                        logger.info("msg ID is {}",msgExt.getMsgId());
                    try {
                        // Jackson 反序列化，注意CustomDateDeserializer的使用
                        JavaTimeModule timeModule =  new JavaTimeModule();
                        timeModule.addDeserializer(LocalDateTime.class, new CustomDateDeserializer());
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.registerModule(timeModule);
                        OrderBO orderBO = objectMapper.readValue(msgExt.getBody(), OrderBO.class);
                        List<OrderBO.itemBo> itemBos = orderBO.getDetail();
                        // 扣减库存
                        itemBos.forEach(itemBo ->
                        {
                            try {
                                shopStockService.decrStock(itemBo.getItemUuid(),itemBo.getQuantity());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                return null;
            }
        });
        defaultMQPushConsumer.start();
        logger.info("stock app DefaultMQPushConsumer initiated success >>> ");
        return  defaultMQPushConsumer;
    }

    @Bean(name = "StockDefaultLitePullConsumer")
    public DefaultLitePullConsumer defaultLitePullConsumer() throws MQClientException {
        DefaultLitePullConsumer defaultLitePullConsumer =  new DefaultLitePullConsumer();
        defaultLitePullConsumer.registerTopicMessageQueueChangeListener("ShopTopic",new TopicMessageQueueChangeListener(){

            @Override
            public void onChanged(String topic, Set<MessageQueue> messageQueues) {
                messageQueues.forEach(messageQueue -> System.out.println(messageQueue.getTopic()));
            }
        });
        logger.info("stock app DefaultLitePullConsumer initiated success >>> ");
        return  defaultLitePullConsumer;
    }
}
