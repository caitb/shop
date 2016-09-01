package com.masiis.shop.api.bean.family;

import com.masiis.shop.api.bean.base.BasePagingReq;

/**
 * Created by wangbingjian on 2016/8/23.
 */
public class TeamListReq extends BasePagingReq {

    private Integer userSkuId;

    public Integer getUserSkuId() {
        return userSkuId;
    }

    public void setUserSkuId(Integer userSkuId) {
        this.userSkuId = userSkuId;
    }
}
