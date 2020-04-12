package com.biao.shop.business.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biao.shop.business.mq.RocketMQTransProducer;
import com.biao.shop.business.service.ShopBusinessService;
import com.biao.shop.common.bo.OrderBo;
import com.biao.shop.common.constant.Constant;
import com.biao.shop.common.controller.BaseController;
import com.biao.shop.common.dto.OrderDto;
import com.biao.shop.common.dto.ShopItemEntityDto;
import com.biao.shop.common.entity.ShopOrderEntity;
import com.biao.shop.common.enums.RespStatusEnum;
import com.biao.shop.common.response.ObjectResponse;
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
import org.dromara.soul.client.common.annotation.SoulClient;
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
@RequestMapping("/business")
@Slf4j
public class ShopBusinessController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(ShopBusinessController.class);

    private ShopBusinessService businessService;
    private RocketMQTransProducer rocketMQTransProducer;
    private DefaultMQProducer defaultMQProducer;

    @Autowired
    public ShopBusinessController(ShopBusinessService businessService, RocketMQTransProducer rocketMQTransProducer,
                                  DefaultMQProducer defaultMQProducer){
        this.businessService = businessService;
        this.rocketMQTransProducer = rocketMQTransProducer;
        this.defaultMQProducer = defaultMQProducer;
    }

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public List<ShopOrderEntity> listOrder(){
        return businessService.listOrder("10");
    }

    // 测试mysql数据保存功能
    @SoulClient(path = "/vehicle/business/create", desc = "创建一个订单")
    @PostMapping(value = "/create") //@PostMapping等价于@RequestMapping(method = RequestMethod.POST)
    public ObjectResponse<Integer> saveOrderDTO(@RequestBody OrderDto orderDTO){

        // 临时需要在web层使用request或response的方式如下； 如频繁使用，可以使用继承一个BaseController
        /*ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();*/

        // 继承自BaseController
        /*String idempotentId = request.getHeader(Constant.IDEMPOTENT_TOKEN);*/

        int result  = businessService.saveOrderDTO(orderDTO);
        ObjectResponse<Integer> response = new ObjectResponse<>();
        response.setCode(RespStatusEnum.SUCCESS.getCode());
        response.setMessage(RespStatusEnum.SUCCESS.getMessage());
        response.setData(result);
        return response;
    }

    @SoulClient(path = "/vehicle/business/update", desc = "更新一个订单")
    @PostMapping(value = "/update") //@PostMapping等价于@RequestMapping(method = RequestMethod.POST)
    public ObjectResponse<Integer> updateOrderDTO(@RequestBody OrderDto orderDTO){
        int result  = businessService.updateOrderDTO(orderDTO);
        ObjectResponse<Integer> response = new ObjectResponse<>();
        response.setCode(RespStatusEnum.SUCCESS.getCode());
        response.setMessage(RespStatusEnum.SUCCESS.getMessage());
        response.setData(result);
        return response;
    }

    // 测试RocketMq事务消息功能
    @PostMapping(value = "/saveMqPaid")
    public TransactionSendResult saveTransOrder(@RequestBody OrderBo order) throws UnsupportedEncodingException,
            MQClientException, JsonProcessingException {
        logger.debug(order.toString());
        //jackson 序列化, 但要注意时间的序列化
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

    // 测试mybatis分页查询功能
    @GetMapping(value = "/pages") //@PostMapping等价于@RequestMapping(method = RequestMethod.POST)
    public Page<ShopOrderEntity> queryOrderPage(){
        Page<ShopOrderEntity> result= businessService.queryOrderPagination(1,3);
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

    @SoulClient(path = "/vehicle/business/order/**", desc = "获取一个OrderBO")
    @GetMapping(value = "/order/{idOrder}")
    public OrderBo getOrderBO(@PathVariable("idOrder") int idOrder){
        logger.info("if this log printed, DB query is invoked.");
        return businessService.getOrderBO(idOrder);
    }
}

