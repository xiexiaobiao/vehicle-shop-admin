package com.biao.shop.common.service;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

public interface RocketMqService {
    SendResult sendMsg(Message msg);
}
