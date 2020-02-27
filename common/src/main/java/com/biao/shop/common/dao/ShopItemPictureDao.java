package com.biao.shop.common.dao;

import com.biao.shop.common.entity.ShopItemPictureEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 商品图片 Mapper 接口
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-02-27
 */
@Mapper
@Repository
public interface ShopItemPictureDao extends BaseMapper<ShopItemPictureEntity> {

}
