package com.biao.shop.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.shop.common.dao.ShopOrderDao;
import com.biao.shop.common.dto.ClientQueryDTO;
import com.biao.shop.common.dto.OrderDto;
import com.biao.shop.common.entity.ItemListEntity;
import com.biao.shop.common.entity.ShopClientEntity;
import com.biao.shop.common.entity.ShopOrderEntity;
import com.biao.shop.common.enums.RespStatusEnum;
import com.biao.shop.common.response.ObjectResponse;
import com.biao.shop.common.rpc.service.ShopClientRPCService;
import com.biao.shop.common.rpc.service.ShopStockRPCService;
import com.biao.shop.order.service.ItemListService;
import com.biao.shop.order.service.OrderService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName OrderServiceImpl
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/15
 * @Version V1.0
 **/
@Service
public class OrderServiceImpl extends ServiceImpl<ShopOrderDao, ShopOrderEntity>  implements OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private ShopOrderDao shopOrderDao;
    private ItemListService itemListService;

    @Reference(version = "1.0.0",group = "shop",interfaceClass = ShopClientRPCService.class)
    private ShopClientRPCService clientRPCService;

    @Reference(version = "1.0.0",group = "shop",interfaceClass = ShopStockRPCService.class)
    // 商品 + 库存
    private ShopStockRPCService stockRPCService;

    @Autowired
    public OrderServiceImpl(ShopOrderDao shopOrderDao,ItemListService itemListService){
        this.shopOrderDao = shopOrderDao;
        this.itemListService =  itemListService;
    }

    @Override
    public int deleteOrderByUuid(String uuid) {
        return shopOrderDao.delete(new LambdaQueryWrapper<ShopOrderEntity>().eq(ShopOrderEntity::getOrderUuid,uuid));
    }

    @Override
    public int deleteBatchByIds(Collection<Integer> ids) {
        ids.forEach(this::deleteById);
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(int id) {
        ShopOrderEntity shopOrderEntity = shopOrderDao.selectById(id);
        String orderUuid = shopOrderEntity.getOrderUuid();
        // 删除订单详细
        List<Integer> list = itemListService.listDetail(orderUuid).stream()
                .map(ItemListEntity::getIdList).collect(Collectors.toList());
        itemListService.deleteBatchItemList(list);
        // 库存（-冻结）
        List<ItemListEntity> itemListEntities = itemListService.listDetail(shopOrderEntity.getOrderUuid());
        itemListEntities.forEach(itemListEntity -> {
            try {
                stockRPCService.unfrozenStock(itemListEntity.getItemUuid(),itemListEntity.getQuantity());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return shopOrderDao.deleteById(id);
    }


    @Override
    public ShopOrderEntity selectByUuId(String uuid) {
        return shopOrderDao.selectOne(new LambdaQueryWrapper<ShopOrderEntity>().eq(ShopOrderEntity::getOrderUuid,uuid));
    }

    @Override
    public int updateOrder(ShopOrderEntity orderEntity) {
        return shopOrderDao.updateById(orderEntity);
    }

    @Override
    public int createOrder(ShopOrderEntity orderEntity) {
        return shopOrderDao.insert(orderEntity);
    }

    @Override
    public ShopOrderEntity queryOrder(int id) {
        return shopOrderDao.selectById(id);
    }

    @Override
    public Page<OrderDto> listOrderDTO(Integer current, Integer size, String orderUuid, String clientName, String phone,
                                       String vehicleSeries, String vehiclePlate, String generateDateStart,
                                       String generateDateEnd, int paidStatus) {
        // 先查询客户信息
        ClientQueryDTO clientQueryDTO = new ClientQueryDTO();
        clientQueryDTO.setClientName(clientName);
        clientQueryDTO.setPhone(phone);
        clientQueryDTO.setVehiclePlate(vehiclePlate);
        clientQueryDTO.setVehicleSeries(vehicleSeries);
        List<ShopClientEntity> clientEntities1 = clientRPCService.listByClientDto(clientQueryDTO);
        List<String> clientUidS = clientEntities1.stream().map(ShopClientEntity::getClientUuid).collect(Collectors.toList());
        // 再查订单信息
        boolean flag = clientUidS.size() > 0;
        boolean dateFlag = StringUtils.isNotEmpty(generateDateStart) && StringUtils.isNotEmpty(generateDateEnd);
        QueryWrapper<ShopOrderEntity> qw = new QueryWrapper<>();
        Map<String,Object> map = new HashMap<>(2);
        map.put("order_uuid",orderUuid);
        map.put("is_paid", paidStatus == 2 ? null : paidStatus);
        qw.allEq(true,map,false)
                .between(dateFlag,"generate_date",generateDateStart,generateDateEnd)
                .in(flag,"client_uuid",clientUidS);
        Page<ShopOrderEntity> orderEntityPage = new Page<>(current,size);
        orderEntityPage = shopOrderDao.selectPage(orderEntityPage,qw);
        List<OrderDto> orderDTOS = orderEntityPage.getRecords().stream().map(orderEntity -> {
            OrderDto orderDTO = new OrderDto();
            BeanUtils.copyProperties(orderEntity, orderDTO);
            ShopClientEntity clientEntity = clientRPCService.queryById(orderEntity.getClientUuid());
            orderDTO.setClientName(clientEntity.getClientName());
            orderDTO.setVehiclePlate(clientEntity.getVehiclePlate());
            return orderDTO;
        }).collect(Collectors.toList());
        Page<OrderDto> orderDTOPage = new Page<>(current,size);
        BeanUtils.copyProperties(orderEntityPage,orderDTOPage,"records");
        return orderDTOPage.setRecords(orderDTOS);

        /** 这里使用 PageInfo 分页，发现有问题，sql只能是 LIMIT (size)，导致无法分页，故改为 mbp 分页*/
        /*PageHelper.startPage(current,size);
        logger.debug("current>>>>>>>{}//{}}",current,size);
        List<ShopOrderEntity> orderEntities = shopOrderDao.selectList(qw);
        PageInfo<ShopOrderEntity> pageInfo = PageInfo.of(orderEntities);
        List<OrderDTO> orderDTOList = pageInfo.getList().stream().map(orderEntity -> {
            OrderDTO orderDTO  = new OrderDTO();
            BeanUtils.copyProperties(orderEntity,orderDTO);
            orderDTO.setClientName(clientRPCService.queryById(orderEntity.getClientUuid()).getClientName());
            return orderDTO;
        }).collect(Collectors.toList());
        return PageInfo.of(orderDTOList);*/

    }


    // 将未付款订单更新为已付款
    @Override
    public int paidOrder(String orderUId,String note) throws Exception {
        ShopOrderEntity orderEntity = this.selectByUuId(orderUId);
        if (Objects.isNull(orderEntity)) {
            return 0;
        } else{
            if (orderEntity.getPaidStatus()){
                throw new Exception("paid order can't be paid again");
            }
            orderEntity.setPaidStatus(true);
            orderEntity.setModifyDate(LocalDateTime.now());
            if(!Objects.isNull(note)){
                orderEntity.setOrderRemark(StringUtils.isEmpty(orderEntity.getOrderRemark())?"":orderEntity.getOrderRemark() +"/"+ note);
            }
            // 加积分
            clientRPCService.addPoint(orderEntity.getClientUuid(),orderEntity.getAmount().intValue());
            // 库存（-冻结+扣减）
            List<ItemListEntity> itemListEntities = itemListService.listDetail(orderEntity.getOrderUuid());
            itemListEntities.forEach(itemListEntity -> {
                try {
                    stockRPCService.unfrozenStock(itemListEntity.getItemUuid(),itemListEntity.getQuantity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    stockRPCService.decreaseStock(itemListEntity.getItemUuid(),itemListEntity.getQuantity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        return shopOrderDao.updateById(orderEntity);
    }

    @Override
    public int cancelOrder(String UuId, String note) throws Exception {
        ShopOrderEntity orderEntity = this.selectByUuId(UuId);
        if (Objects.isNull(orderEntity)) {
            return 0;
        } else{
            if (orderEntity.getPaidStatus()){
                throw new Exception("paid order can't be canceled");
            }
            orderEntity.setCancelStatus(true);
            orderEntity.setModifyDate(LocalDateTime.now());
            if(!Objects.isNull(note)){
                orderEntity.setOrderRemark(StringUtils.isEmpty(orderEntity.getOrderRemark())?"":orderEntity.getOrderRemark() +"/"+ note);
            }
            // 库存（-冻结）
            List<ItemListEntity> itemListEntities = itemListService.listDetail(orderEntity.getOrderUuid());
            itemListEntities.forEach(itemListEntity -> {
                try {
                    stockRPCService.unfrozenStock(itemListEntity.getItemUuid(),itemListEntity.getQuantity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        return shopOrderDao.updateById(orderEntity);
    }

    @Override
    public ObjectResponse<Integer> autoCancelOrder() {
        ObjectResponse<Integer> response = new ObjectResponse<>();
        try{
            // 查找当天30分钟内未付款订单
            List<ShopOrderEntity> orderEntityList = shopOrderDao.selectList(new LambdaQueryWrapper<ShopOrderEntity>()
                    .gt(ShopOrderEntity::getGenerateDate, LocalDate.now())
                    .lt(ShopOrderEntity::getGenerateDate,LocalDateTime.now().minusMinutes(30L)));
            if (!Objects.isNull(orderEntityList) && !orderEntityList.isEmpty()){
                int result = shopOrderDao.deleteBatchIds(orderEntityList);
                response.setCode(RespStatusEnum.SUCCESS.getCode());
                response.setMessage(RespStatusEnum.SUCCESS.getMessage());
                response.setData(result);
            }
            return response;
        }catch (Exception e){
            response.setCode(RespStatusEnum.FAIL.getCode());
            response.setMessage(RespStatusEnum.FAIL.getMessage());
            response.setData(null);
            return response;
        }
    }

    /**
     * 这里为了演示http模式，直接使用参数：
     *      url: http://127.0.0.1:9195/order/vehicle/order/autoCancel
     *      method: get
     *      data: content
     */
    @XxlJob("autoCancelOrderJobHandler")
    public ReturnT<String> autoCancelOrderJob( String param ){
        // param parse
        if (param==null || param.trim().length()==0) {
            XxlJobLogger.log("param["+ param +"] invalid.");
            return ReturnT.FAIL;
        }
        String[] httpParams = param.split("\n");
        String url = null;
        String method = null;
        String data = null;
        for (String httpParam: httpParams) {
            if (httpParam.startsWith("url:")) {
                url = httpParam.substring(httpParam.indexOf("url:") + 4).trim();
            }
            if (httpParam.startsWith("method:")) {
                method = httpParam.substring(httpParam.indexOf("method:") + 7).trim().toUpperCase();
                System.out.println("method>>>>>>>>"+ method);
            }
            if (httpParam.startsWith("data:")) {
                data = httpParam.substring(httpParam.indexOf("data:") + 5).trim();
            }
        }

        // param valid
        if (url==null || url.trim().length()==0) {
            XxlJobLogger.log("url["+ url +"] invalid.");
            return ReturnT.FAIL;
        }
        // 限制只支持 "GET" 和 "POST"
        if (method==null || !Arrays.asList("GET", "POST").contains(method)) {
            XxlJobLogger.log("method["+ method +"] invalid.");
            return ReturnT.FAIL;
        }

        // request
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        try {
            // connection
            URL realUrl = new URL(url);
            connection = (HttpURLConnection) realUrl.openConnection();

            // connection setting
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setReadTimeout(5 * 1000);
            connection.setConnectTimeout(3 * 1000);
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setRequestProperty("Accept-Charset", "application/json;charset=UTF-8");

            // do connection
            connection.connect();

            // data
            if (data!=null && data.trim().length()>0) {
                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                dataOutputStream.write(data.getBytes("UTF-8"));
                dataOutputStream.flush();
                dataOutputStream.close();
            }

            // valid StatusCode
            int statusCode = connection.getResponseCode();
            if (statusCode != 200) {
                throw new RuntimeException("Http Request StatusCode(" + statusCode + ") Invalid.");
            }

            // result
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            String responseMsg = result.toString();

            XxlJobLogger.log(responseMsg);
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            XxlJobLogger.log(e);
            return ReturnT.FAIL;
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e2) {
                XxlJobLogger.log(e2);
            }
        }
    }

}
