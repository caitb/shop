package com.masiis.shop.api.bean.family;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

public class MyTeamReq extends BaseBusinessReq {

    private Integer userSkuId;

    public Integer getUserSkuId() {
        return userSkuId;
    }

    public void setUserSkuId(Integer userSkuId) {
        this.userSkuId = userSkuId;
    }
}
