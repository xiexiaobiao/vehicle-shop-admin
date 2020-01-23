package com.biao.shop.customer.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biao.shop.common.entity.ShopClientEntity;
import com.biao.shop.common.dao.ShopClientDao;
import com.biao.shop.common.service.ShopClientService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.dromara.soul.client.common.annotation.SoulClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * �ͻ��� ����ʵ����
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-01-06
 */
@org.springframework.stereotype.Service
@Service(version = "1.0",group = "shop",interfaceClass = ShopClientService.class)
@Slf4j
@SoulClient(path = "/", desc = "customer provider")
public class ShopClientServiceImpl extends ServiceImpl<ShopClientDao, ShopClientEntity> implements ShopClientService {

    private ShopClientDao shopClientDao;

    @Autowired
    public ShopClientServiceImpl(ShopClientDao shopClientDao){
        this.shopClientDao = shopClientDao;
    }
    @Override
    public int addClient(ShopClientEntity clientEntity) {
        return 0;
    }

    @Override
    public int deleteClient(String uuid) {
        return 0;
    }

    @Override
    public int modifyClient(ShopClientEntity clientEntity) {
        return 0;
    }

    @Override
    public List<ShopClientEntity> queryClient(String condition) {
        return null;
    }

    @Override
    @Transactional
    public int addPoint(String uuid,int pointToAdd) {
        ShopClientEntity clientEntity =  this.queryById(uuid);
        log.debug(clientEntity.toString());
        clientEntity.setPoint(Objects.isNull(clientEntity.getPoint()) ? 0:clientEntity.getPoint() + pointToAdd);
        return shopClientDao.updateById(clientEntity);
    }

    @Override
    public ShopClientEntity queryById(String uuid) {
        QueryWrapper<ShopClientEntity> qw = new QueryWrapper<>();
        qw.eq(true,"uuid",uuid);
        return shopClientDao.selectOne(qw);
    }
}
