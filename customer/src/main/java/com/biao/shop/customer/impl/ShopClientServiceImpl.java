package com.biao.shop.customer.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.shop.common.dao.ShopClientDao;
import com.biao.shop.common.dto.ClientQueryDTO;
import com.biao.shop.common.entity.ShopClientEntity;
import com.biao.shop.customer.service.ShopClientService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-01-06
 */
@org.springframework.stereotype.Service
@Slf4j
public class ShopClientServiceImpl extends ServiceImpl<ShopClientDao, ShopClientEntity> implements ShopClientService {

    private final Logger logger = LoggerFactory.getLogger(ShopClientServiceImpl.class);

    private ShopClientDao shopClientDao;

    @Autowired
    public ShopClientServiceImpl(ShopClientDao shopClientDao){
        this.shopClientDao = shopClientDao;
    }

    @Override
    public String getMaxClientUuId() {
        return shopClientDao.selectList(new LambdaQueryWrapper<ShopClientEntity>()
                .isNotNull(ShopClientEntity::getClientUuid).orderByDesc(ShopClientEntity::getClientUuid))
                .stream().limit(1).collect(Collectors.toList())
                .get(0).getClientUuid();
    }

    @Override
    public int createClient(ShopClientEntity clientEntity) {
        clientEntity.setGenerateDate(LocalDateTime.now());
        return shopClientDao.insert(clientEntity);
    }

    @Override
//    @CacheEvict(cacheNames = "shopClient")
    public int deleteBatchById(Collection<Integer> ids) {
        logger.info("删除Redis缓存");
        return shopClientDao.deleteBatchIds(ids);
    }

    @Override
//    @CacheEvict(cacheNames = "shopClient")
    public int deleteById(int id) {
        logger.info("删除Redis缓存");
        return shopClientDao.deleteById(id);
    }

    @Override
//    @CacheEvict(cacheNames = "shopClient") // 删除Redis缓存
    public int deleteByUUid(String uuid) {
        QueryWrapper<ShopClientEntity> qw = new QueryWrapper<>();
        qw.eq(true,"uuid",uuid);
        return shopClientDao.delete(qw);
    }

    @Override
//    @CachePut(cacheNames = "shopClient")  // 更新Redis缓存
    public int updateClient(ShopClientEntity clientEntity) {
//        logger.info("更新Redis缓存");
        clientEntity.setModifyDate(LocalDateTime.now());
        return shopClientDao.updateById(clientEntity);
    }


    @Override
    public int addPoint(String uuid,int pointToAdd) {
        ShopClientEntity clientEntity =  this.queryByUuId(uuid);
        log.debug(clientEntity.toString());
        clientEntity.setPoint(Objects.isNull(clientEntity.getPoint()) ? 0 : clientEntity.getPoint() + pointToAdd);
        return shopClientDao.updateById(clientEntity);
    }

    @Override
//    @Cacheable(cacheNames = "shopClient")
    public ShopClientEntity queryByUuId(String uuid) {
//        logger.info("queryByUuId 未使用Redis缓存");
        QueryWrapper<ShopClientEntity> qw = new QueryWrapper<>();
        qw.eq(true,"client_uuid",uuid);
        return shopClientDao.selectOne(qw);
    }

    @Override
//    @Cacheable(cacheNames = "shopClient")
    public ShopClientEntity queryById(int id) {
        logger.info("queryById 未使用Redis缓存");
        return shopClientDao.selectById(id);
    }

    @Override
//    @Cacheable(cacheNames = "shopClient")
    public PageInfo<ShopClientEntity> listClient(Integer current, Integer size, String clientUuid, String name,
                                                 String vehiclePlate, String phone) {
        logger.info("listClient 未使用Redis缓存");
        QueryWrapper<ShopClientEntity> qw = new QueryWrapper<>();
        Map<String,Object> map = new HashMap<>(4);
        map.put("client_uuid",clientUuid);
        map.put("vehicle_plate",vehiclePlate);
        map.put("phone",phone);
        boolean valid = Objects.isNull(name); // "name" 模糊匹配
        qw.allEq(true,map,false).like(!valid,"client_name",name);
        PageHelper.startPage(current,size);
        List<ShopClientEntity> clientEntities = shopClientDao.selectList(qw);
        return  PageInfo.of(clientEntities);
    }

    // java Stream
    @Override
//    @Cacheable(cacheNames = "shopClient")
    public List<String> listPlate() {
        logger.info("listPlate 未使用Redis缓存");
        List<ShopClientEntity> clientEntities =
                shopClientDao.selectList(new LambdaQueryWrapper<ShopClientEntity>().isNotNull(ShopClientEntity::getVehiclePlate));
        return clientEntities.stream().map(ShopClientEntity::getVehiclePlate).collect(Collectors.toList());
    }

    @Override
    public List<ShopClientEntity> listByClientDto(ClientQueryDTO clientQueryDTO) {
        QueryWrapper<ShopClientEntity> qw = new QueryWrapper<>();
        boolean phoneFlag = Objects.isNull(clientQueryDTO.getPhone());
        boolean clientNameFlag = Objects.isNull(clientQueryDTO.getClientName());
        boolean VehicleSeriesFlag = Objects.isNull(clientQueryDTO.getVehicleSeries());
        boolean VehiclePlateFlag = Objects.isNull(clientQueryDTO.getVehiclePlate());
        qw.eq(!phoneFlag,"phone",clientQueryDTO.getPhone())  //如有null的条件直接不参与查询
                .like(!clientNameFlag,"client_name",clientQueryDTO.getClientName())
                .like(!VehiclePlateFlag,"vehicle_plate",clientQueryDTO.getVehiclePlate())
                .like(!VehicleSeriesFlag,"vehicle_series",clientQueryDTO.getVehicleSeries());
        return shopClientDao.selectList(qw);
    }
}
