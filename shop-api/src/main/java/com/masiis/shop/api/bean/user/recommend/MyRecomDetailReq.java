package com.masiis.shop.api.bean.user.recommend;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * Created by wangbingjian on 2016/8/6.
 */
public class MyRecomDetailReq extends BaseBusinessReq {

    private Long userId;

    private Integer skuId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }
}
