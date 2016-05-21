package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * Created by cai_tb on 16/5/20.
 */
public class PopularizeReq extends BaseBusinessReq {

    private Integer userSkuId;

    public Integer getUserSkuId() {
        return userSkuId;
    }

    public void setUserSkuId(Integer userSkuId) {
        this.userSkuId = userSkuId;
    }

}
