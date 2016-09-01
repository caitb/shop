package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

public class DevelopPosterReq extends BaseBusinessReq {

    private Integer brandId;

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }
}
