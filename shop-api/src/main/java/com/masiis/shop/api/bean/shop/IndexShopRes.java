package com.masiis.shop.api.bean.shop;

import com.masiis.shop.api.bean.base.BasePagingReq;
import com.masiis.shop.api.bean.base.BasePagingRes;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfShop;

/**
 * @Date 2016/5/5
 * @Auther lzh
 */
public class IndexShopRes extends BasePagingRes {
    private String token;
    private ComUser comUser;
    private SfShop sfShop;
    private Integer orderCount;
    private Integer shopView;
    private String shopUrl;
    private String saleAmount;//总销售额
    private Integer fansNum;//粉丝数
    private Integer unshippedOrderCount;//待发货订单数
    private String shopPoster;

    public Integer getUnshippedOrderCount() {
        return unshippedOrderCount;
    }

    public void setUnshippedOrderCount(Integer unshippedOrderCount) {
        this.unshippedOrderCount = unshippedOrderCount;
    }

    public Integer getFansNum() {
        return fansNum;
    }

    public void setFansNum(Integer fansNum) {
        this.fansNum = fansNum;
    }

    public String getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(String saleAmount) {
        this.saleAmount = saleAmount;
    }

    public void setComUser(ComUser comUser) {
        this.comUser = comUser;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public void setSfShop(SfShop sfShop) {
        this.sfShop = sfShop;
    }


    public void setToken(String token) {
        this.token = token;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public void setShopView(Integer shopView) {
        this.shopView = shopView;
    }

    public ComUser getComUser() {
        return comUser;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public Integer getShopView() {
        return shopView;
    }

    public SfShop getSfShop() {
        return sfShop;
    }

    public String getToken() {
        return token;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public String getShopPoster() {
        return shopPoster;
    }

    public void setShopPoster(String shopPoster) {
        this.shopPoster = shopPoster;
    }
}
