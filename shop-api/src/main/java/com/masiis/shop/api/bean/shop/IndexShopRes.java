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
}
