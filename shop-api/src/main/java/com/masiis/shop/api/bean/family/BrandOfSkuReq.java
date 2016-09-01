package com.masiis.shop.api.bean.family;

import com.masiis.shop.api.bean.base.BasePagingReq;

/**
 * Created by jiajinghao on 2016/8/9.
 */
public class BrandOfSkuReq extends BasePagingReq {

    private Integer brandId;//商品品牌Id

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }
}
