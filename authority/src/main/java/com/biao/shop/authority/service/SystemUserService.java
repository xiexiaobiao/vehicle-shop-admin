package com.biao.shop.authority.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biao.shop.common.entity.SystemUserEntity;

/**
 * <p>
 * 系统管理员表 服务类
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-02-21
 */
public interface SystemUserService extends IService<SystemUserEntity> {

    boolean checkPasswd(String user,String passwd);
}
