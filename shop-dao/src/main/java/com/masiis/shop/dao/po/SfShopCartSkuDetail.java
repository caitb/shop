package com.masiis.shop.dao.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by hzz on 2016/4/9.
 *
 * 购物车商品的详情信息
 */
public class SfShopCartSkuDetail {

    /*小铺购物车主键id*/
    private Long shopCartId;

    /*数量*/
    private Integer quantity;

    /*小铺的id*/
    private Long sfShopId;

    /*小铺归属人id*/
    private Long sfShopUserId;

    /*商品的总价格 = 商品的价格*数量  */
    private BigDecimal skuSumPrice;

    /*商品的详情信息*/
    private ComSku comSku;

    /**
     * 商品主图
     */
    private String skuImg;

    public Long getShopCartId() {
        return shopCartId;
    }

    public void setShopCartId(Long shopCartId) {
        this.shopCartId = shopCartId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSkuSumPrice() {
        return skuSumPrice;
    }

    public void setSkuSumPrice(BigDecimal skuSumPrice) {
        this.skuSumPrice = skuSumPrice;
    }

    public ComSku getComSku() {
        return comSku;
    }

    public void setComSku(ComSku comSku) {
        this.comSku = comSku;
    }

    public Long getSfShopId() {
        return sfShopId;
    }

    public void setSfShopId(Long sfShopId) {
        this.sfShopId = sfShopId;
    }

    public Long getSfShopUserId() {
        return sfShopUserId;
    }

    public void setSfShopUserId(Long sfShopUserId) {
        this.sfShopUserId = sfShopUserId;
    }

    public String getSkuImg() {
        return skuImg;
    }

    public void setSkuImg(String skuImg) {
        this.skuImg = skuImg;
    }
}
