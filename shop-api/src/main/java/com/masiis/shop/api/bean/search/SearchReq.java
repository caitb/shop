package com.masiis.shop.api.bean.search;


import com.masiis.shop.api.bean.base.BaseBusinessReq;

/**
 * Created by cai_tb on 16/8/9.
 */
public class SearchReq extends BaseBusinessReq {

    private Long userId;
    private String mobile;
    private Integer brandId;//品牌id(可选参数)

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }
}
