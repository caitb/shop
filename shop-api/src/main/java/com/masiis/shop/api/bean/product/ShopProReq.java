package com.masiis.shop.api.bean.product;

import com.masiis.shop.api.bean.base.BaseReq;

/**
 * Created by JingHao on 2016/5/26 0026.
 */
public class ShopProReq extends BaseReq{

    private String token;

    private Long shopId;//店铺id

    private Integer isSale;//上下架标志

    private Long shopSkuId;//库存id

    private Integer isOwnShip;//是否自己发货

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getIsSale() {
        return isSale;
    }

    public void setIsSale(Integer isSale) {
        this.isSale = isSale;
    }

    public Long getShopSkuId() {
        return shopSkuId;
    }

    public void setShopSkuId(Long shopSkuId) {
        this.shopSkuId = shopSkuId;
    }

    public Integer getIsOwnShip() {
        return isOwnShip;
    }

    public void setIsOwnShip(Integer isOwnShip) {
        this.isOwnShip = isOwnShip;
    }
}
