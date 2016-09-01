package com.masiis.shop.api.bean.family;

import com.masiis.shop.api.bean.base.BaseReq;

/**
 * Created by jiajinghao on 2016/8/13.
 */
public class AgentSecondarySkuReq extends BaseReq {

    private String token;

    private Integer skuId;

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
