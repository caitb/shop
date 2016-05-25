package com.masiis.shop.api.bean.product;

import com.masiis.shop.api.bean.base.BaseReq;

/**
 * Created by JingHao on 2016/5/24 0024.
 */
public class AddProReq extends BaseReq{

    private String token;

    private Integer userSkuId;//商品id

    private Integer quantity;//补货数量

    private String userMessage;//用户留言

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserSkuId() {
        return userSkuId;
    }

    public void setUserSkuId(Integer userSkuId) {
        this.userSkuId = userSkuId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }
}
