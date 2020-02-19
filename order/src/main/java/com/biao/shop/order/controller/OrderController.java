package com.biao.shop.order.controller;

import com.biao.shop.common.entity.ItemListEntity;
import com.biao.shop.common.entity.ShopClientEntity;
import com.biao.shop.common.entity.ShopOrderEntity;
import com.biao.shop.order.service.ItemListService;
import com.biao.shop.order.service.OrderService;
import com.github.pagehelper.PageInfo;
import org.dromara.soul.client.common.annotation.SoulClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName OrderController
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/15
 * @Version V1.0
 **/
@RestController
@RequestMapping("/order")
public class OrderController {
    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    private OrderService orderService;
    private ItemListService itemListService;

    @Autowired
    public OrderController(OrderService orderService,ItemListService itemListService){
        this.orderService = orderService;
        this.itemListService =  itemListService;
    }

    @SoulClient(path = "/vehicle/order/list", desc = "获取订单列表")
    @GetMapping("/list")
    public PageInfo<ShopOrderEntity> listOrder(@RequestParam("pageNum")Integer current, @RequestParam("pageSize")Integer size,
                                                @RequestParam(value = "orderUuid",required = false) String orderUuid,
                                                @RequestParam(value = "clientName",required = false) String clientName,
                                                @RequestParam(value = "phone",required = false) String phone,
                                                @RequestParam(value = "vehicleSeries",required = false)String vehicleSeries,
                                                @RequestParam(value = "vehiclePlate",required = false)String vehiclePlate,
                                                @RequestParam(value = "generateDateStart",required = false)String generateDateStart,
                                               @RequestParam(value = "generateDateEnd",required = false)String generateDateEnd,
                                                @RequestParam(value = "paidStatus",required = false)boolean paidStatus){
        return orderService.listOrder(current,size,orderUuid,clientName,phone,vehicleSeries,
                vehiclePlate,generateDateStart,generateDateEnd,paidStatus);
    }

    @SoulClient(path = "/vehicle/order/itemList", desc = "获取订单商品列表")
    @GetMapping("/itemList")
    public List<ItemListEntity> itemList(@RequestParam("orderUuid")String orderUuid){
        return itemListService.listDetail(orderUuid);
    }

    @SoulClient(path = "/vehicle/order/**", desc = "获取订单商品列表")
    @GetMapping("/{id}")
    public ShopOrderEntity getOrder(@PathVariable("id") int id){
        return orderService.queryOrder(id);
    }

    @SoulClient(path = "/vehicle/order/del", desc = "删除订单")
    @GetMapping("/del")
    public int deleteOrder(@RequestParam("ids") String ids){
        if (ids.contains(",")){
            List<Integer> list = new ArrayList<>(8);
            String[] strings = ids.split(",");
            for (int i = 0; i < strings.length; i++) {
                list.add(Integer.valueOf(strings[i]));
            }
            return orderService.deleteBatchByIds(list);
        }
        return orderService.deleteById(Integer.parseInt(ids));
    }
}
