package com.biao.shop.common.dao;

import com.biao.shop.common.entity.SystemUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统管理员表 Mapper 接口
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-02-21
 */
@Repository
@Mapper
public interface SystemUserDao extends BaseMapper<SystemUserEntity> {

}
