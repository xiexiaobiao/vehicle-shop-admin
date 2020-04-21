package com.biao.shop.customer.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.shop.common.dao.ShopClientDao;
import com.biao.shop.common.dto.ClientQueryDTO;
import com.biao.shop.common.entity.ShopClientEntity;
import com.biao.shop.common.utils.MailUtil;
import com.biao.shop.customer.service.ShopClientService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.time.LocalDate;
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

    @Value("${spring.mail.receiver.addr}")
    private String mailReceiverAddr;

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
    @Caching(put = @CachePut(cacheNames = {"shopClient"},key = "#root.args[0].clientUuid"),
            evict = @CacheEvict(cacheNames = {"shopClientPage","shopClientPlateList","shopClientList"},allEntries = true))
    public int createClient(ShopClientEntity clientEntity) {
        clientEntity.setGenerateDate(LocalDateTime.now());
        return shopClientDao.insert(clientEntity);
    }

    /** */
    @Override
    @CacheEvict(cacheNames = {"shopClient","shopClientPage","shopClientPlateList","shopClientList"},allEntries = true)
    public int deleteBatchById(Collection<Integer> ids) {
        logger.info("deleteBatchById 删除Redis缓存");
        return shopClientDao.deleteBatchIds(ids);
    }

    @Override
    @CacheEvict(cacheNames = {"shopClient","shopClientPage","shopClientPlateList","shopClientList"},allEntries = true)
    public int deleteById(int id) {
        logger.info("deleteById 删除Redis缓存");
        return shopClientDao.deleteById(id);
    }

    @Override
    @Caching(evict = {@CacheEvict(cacheNames = "shopClient",key = "#root.args[0]"),
            @CacheEvict(cacheNames = {"shopClientPage","shopClientPlateList","shopClientList"},allEntries = true)})
    public int deleteByUUid(String uuid) {
        logger.info("deleteByUUid 删除Redis缓存");
        QueryWrapper<ShopClientEntity> qw = new QueryWrapper<>();
        qw.eq(true,"uuid",uuid);
        return shopClientDao.delete(qw);
    }

    @Override
    @Caching(put = @CachePut(cacheNames = "shopClient",key = "#root.args[0].clientUuid"),
            evict = @CacheEvict(cacheNames = {"shopClientPage","shopClientPlateList","shopClientList"},allEntries = true))
    public int updateClient(ShopClientEntity clientEntity) {
        logger.info("updateClient 更新Redis缓存");
        clientEntity.setModifyDate(LocalDateTime.now());
        return shopClientDao.updateById(clientEntity);
    }


    @Override
    @CacheEvict(cacheNames = {"shopClient","shopClientPage","shopClientPlateList","shopClientList"},allEntries = true)
    public int addPoint(String uuid,int pointToAdd) {
        ShopClientEntity clientEntity =  this.queryByUuId(uuid);
        log.debug(clientEntity.toString());
        clientEntity.setPoint(Objects.isNull(clientEntity.getPoint()) ? 0 : clientEntity.getPoint() + pointToAdd);
        return shopClientDao.updateById(clientEntity);
    }

    @Override
    @Cacheable(cacheNames = "shopClient",key = "#root.args[0]")
    public ShopClientEntity queryByUuId(String uuid) {
        logger.info("queryByUuId 未使用Redis缓存");
        QueryWrapper<ShopClientEntity> qw = new QueryWrapper<>();
        qw.eq(true,"client_uuid",uuid);
        return shopClientDao.selectOne(qw);
    }

    @Override
    @Cacheable(cacheNames = "shopClientById",key = "#root.args[0]")
    public ShopClientEntity queryById(int id) {
        logger.info("queryById 未使用Redis缓存");
        return shopClientDao.selectById(id);
    }

    @Override
    @Cacheable(cacheNames = "shopClientPage")
    public PageInfo<ShopClientEntity> listClient(Integer current, Integer size, String clientUuid, String name,
                                                 String vehiclePlate, String phone) {
        logger.info("listClient 未使用Redis缓存");
        QueryWrapper<ShopClientEntity> qw = new QueryWrapper<>();
        Map<String,Object> map = new HashMap<>(4);
        map.put("client_uuid",clientUuid);
        map.put("vehicle_plate",vehiclePlate);
        map.put("phone",phone);
        // "name" 模糊匹配
        boolean valid = Objects.isNull(name);
        qw.allEq(true,map,false).like(!valid,"client_name",name);
        PageHelper.startPage(current,size);
        List<ShopClientEntity> clientEntities = shopClientDao.selectList(qw);
        return  PageInfo.of(clientEntities);
    }

    // java Stream
    @Override
    @Cacheable(cacheNames = "shopClientPlateList")
    public List<String> listPlate() {
        logger.info("listPlate 未使用Redis缓存");
        List<ShopClientEntity> clientEntities =
                shopClientDao.selectList(new LambdaQueryWrapper<ShopClientEntity>().isNotNull(ShopClientEntity::getVehiclePlate));
        return clientEntities.stream().map(ShopClientEntity::getVehiclePlate).collect(Collectors.toList());
    }

    @Override
    @Cacheable(cacheNames = "shopClientList",key = "#root.args[0].toString()")
    public List<ShopClientEntity> listByClientDto(ClientQueryDTO clientQueryDTO) {
        logger.info("listByClientDto 未使用Redis缓存");
        QueryWrapper<ShopClientEntity> qw = new QueryWrapper<>();
        boolean phoneFlag = Objects.isNull(clientQueryDTO.getPhone());
        boolean clientNameFlag = Objects.isNull(clientQueryDTO.getClientName());
        boolean vehicleSeriesFlag = Objects.isNull(clientQueryDTO.getVehicleSeries());
        boolean vehiclePlateFlag = Objects.isNull(clientQueryDTO.getVehiclePlate());
        //如有null的条件直接不参与查询
        qw.eq(!phoneFlag,"phone",clientQueryDTO.getPhone())
                .like(!clientNameFlag,"client_name",clientQueryDTO.getClientName())
                .like(!vehicleSeriesFlag,"vehicle_plate",clientQueryDTO.getVehiclePlate())
                .like(!vehiclePlateFlag,"vehicle_series",clientQueryDTO.getVehicleSeries());
        return shopClientDao.selectList(qw);
    }

    @Override
    @XxlJob("autoSendPromotionJobHandler")
    public ReturnT<String> autoSendPromotion(String param) {
        try{
            // 找出当天注册的用户
            List<ShopClientEntity> clientEntityList =
                    shopClientDao.selectList(new LambdaQueryWrapper<ShopClientEntity>()
                            .gt(ShopClientEntity::getGenerateDate,LocalDate.now())
                            .lt(ShopClientEntity::getGenerateDate,LocalDate.now().plusDays(1L)));
            // 发送邮件信息
            if (!Objects.isNull(clientEntityList) && !clientEntityList.isEmpty()){
                // shopClientEntity中需要设计用户邮箱地址，我这里简化为一个固定的邮箱地址
                clientEntityList.forEach(shopClientEntity -> {
                    try {
                        MailUtil.sendMailTo(shopClientEntity.getClientName(),mailReceiverAddr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            return ReturnT.SUCCESS;
        }catch (Exception e){
            return ReturnT.FAIL;
        }
    }
}
