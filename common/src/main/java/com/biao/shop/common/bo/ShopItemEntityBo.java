package com.biao.shop.common.bo;

import com.biao.shop.common.entity.ShopItemEntity;

import java.util.List;

/**
 * @ClassName ShopItemEntityBo
 * @Description: TODO
 * @Author Biao
 * @Date 2020/2/27
 * @Version V1.0
 **/
public class ShopItemEntityBo extends ShopItemEntity {
    private List<String> albumPics;

    public List<String> getAlbumPics() {
        return albumPics;
    }

    public void setAlbumPics(List<String> albumPics) {
        this.albumPics = albumPics;
    }

    @Override
    public String toString() {
        return super.toString() +
                "ShopItemEntityBo{" +
                "albumPics=" + albumPics +
                '}';
    }
}
