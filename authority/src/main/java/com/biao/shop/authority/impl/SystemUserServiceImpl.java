package com.biao.shop.authority.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.biao.shop.authority.service.SystemUserService;
import com.biao.shop.common.annotation.LogAspect;
import com.biao.shop.common.entity.SystemUserEntity;
import com.biao.shop.common.dao.SystemUserDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 系统管理员表 服务实现类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-02-21
 */
@Service()
public class SystemUserServiceImpl extends ServiceImpl<SystemUserDao, SystemUserEntity> implements SystemUserService {

    @Autowired
    SystemUserDao systemUserDao;

    @Override
    @LogAspect
    public boolean checkPasswd(String user, String passwd) {
        SystemUserEntity userEntity = systemUserDao.selectOne(new LambdaQueryWrapper<SystemUserEntity>()
                .eq(SystemUserEntity::getUserName, user).eq(SystemUserEntity::getUserPasswd, passwd));
        return !Objects.isNull(userEntity);
    }
}
