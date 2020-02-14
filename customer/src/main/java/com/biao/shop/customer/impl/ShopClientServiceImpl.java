package com.biao.shop.customer.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biao.shop.common.entity.ShopClientEntity;
import com.biao.shop.common.dao.ShopClientDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biao.shop.common.entity.ShopItemEntity;
import com.biao.shop.customer.service.ShopClientService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.dromara.soul.client.common.annotation.SoulClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    private ShopClientDao shopClientDao;

    @Autowired
    public ShopClientServiceImpl(ShopClientDao shopClientDao){
        this.shopClientDao = shopClientDao;
    }

    @Override
    public int createClient(ShopClientEntity clientEntity) {
        return shopClientDao.insert(clientEntity);
    }

    @Override
    public int deleteById(int id) {
        return shopClientDao.deleteById(id);
    }

    @Override
    public int deleteByUUid(String uuid) {
        QueryWrapper<ShopClientEntity> qw = new QueryWrapper<>();
        qw.eq(true,"uuid",uuid);
        return shopClientDao.delete(qw);
    }

    @Override
    public int updateClient(ShopClientEntity clientEntity) {
        return shopClientDao.updateById(clientEntity);
    }

    @Override
    public List<ShopClientEntity> queryClient(String condition) {
        return null;
    }

    @Override
    public int addPoint(String uuid,int pointToAdd) {
        ShopClientEntity clientEntity =  this.queryByUuId(uuid);
        log.debug(clientEntity.toString());
        clientEntity.setPoint(Objects.isNull(clientEntity.getPoint()) ? 0 : clientEntity.getPoint() + pointToAdd);
        return shopClientDao.updateById(clientEntity);
    }

    @Override
    public ShopClientEntity queryByUuId(String uuid) {
        QueryWrapper<ShopClientEntity> qw = new QueryWrapper<>();
        qw.eq(true,"uuid",uuid);
        return shopClientDao.selectOne(qw);
    }

    @Override
    public ShopClientEntity queryById(int id) {
        return shopClientDao.selectById(id);
    }

    @Override
    public PageInfo<ShopClientEntity> listClient(Integer current, Integer size, String clientUuid, String name,
                                                 String vehiclePlate, String phone) {
        QueryWrapper<ShopClientEntity> qw = new QueryWrapper<>();
        Map<String,Object> map = new HashMap<>(4);
        map.put("client_uuid",clientUuid);
        map.put("name",name);
        map.put("phone",phone);
        boolean valid = Objects.isNull(vehiclePlate); // "vehicle_plate" 模糊匹配
        qw.allEq(true,map,false).like(!valid,"vehicle_plate",vehiclePlate);
        System.out.println(">>>>>"+ current+">>>>>>>>>>"+size);
        PageHelper.startPage(current,size);
        List<ShopClientEntity> clientEntities = shopClientDao.selectList(qw);
        return  PageInfo.of(clientEntities);
    }
}
