package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * Created by cai_tb on 16/5/25.
 */
public class TeamDetailReq extends BaseBusinessReq {

    private Integer userSkuId;

    public Integer getUserSkuId() {
        return userSkuId;
    }

    public void setUserSkuId(Integer userSkuId) {
        this.userSkuId = userSkuId;
    }
}
