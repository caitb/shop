package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * @Date 2016/5/23
 * @Auther lzh
 */
public class PartnerPreApplyReq extends BaseBusinessReq {
    private Integer skuId;

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }
}
