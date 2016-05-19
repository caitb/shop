package com.masiis.shop.api.bean.product;

import com.masiis.shop.api.bean.base.BaseReq;

/**
 * Created by JingHao on 2016/5/19 0019.
 */
public class ProDetailReq extends BaseReq {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private Integer skuId;

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }
}

